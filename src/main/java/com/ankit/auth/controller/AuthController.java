package com.ankit.auth.controller;

import com.ankit.auth.dto.UserInfoDto;
import com.ankit.auth.entities.RefreshToken;
import com.ankit.auth.request.RefreshTokenRequestDTO;
import com.ankit.auth.response.AuthResponseDTO;
import com.ankit.auth.service.JwtService;
import com.ankit.auth.service.RefreshTokenService;
import com.ankit.auth.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/v1")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private RefreshTokenService refreshTokenService;

    /*
    This will be called when both the token are expired that we had given at signup
     */
    @PostMapping("/signup")
    public ResponseEntity signUpUser(@RequestBody UserInfoDto userInfoDto) {
        try {
            Boolean isSignedupUser = userDetailsServiceImpl.signUpUser(userInfoDto);
            if (isSignedupUser) {
                String accessToken = jwtService.generateToken(userInfoDto.getUsername());
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfoDto.getUsername());
                return new ResponseEntity<>(AuthResponseDTO.builder().accessToken(accessToken)
                        .refreshToken(refreshToken.getToken()).build(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    This will be used when the user is already in the DB with token, when signed up (access and refresh token will be provided )
    So it will check from the token comes from the api body, that the user exists or not
    If the refresh token is also expired, then user should have to log-in again
     */
    @PostMapping("/refreshToken")
    public ResponseEntity refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.generateToken(userInfo.getUsername());
                    return new ResponseEntity<>(AuthResponseDTO.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequestDTO.getToken()).build(), HttpStatus.OK);
                }).orElseThrow(() -> new RuntimeException("Refresh token is not in DB"));
    }
}
