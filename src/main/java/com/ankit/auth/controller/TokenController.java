package com.ankit.auth.controller;


import com.ankit.auth.request.AuthRequestDTO;
import com.ankit.auth.response.AuthResponseDTO;
import com.ankit.auth.service.JwtService;
import com.ankit.auth.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/v2")
public class TokenController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
            if (authentication.isAuthenticated()) {
                return new ResponseEntity<>(AuthResponseDTO.builder()
                        .accessToken(jwtService.generateToken(authRequestDTO.getUsername()))
                        .refreshToken(refreshTokenService.createRefreshToken(authRequestDTO.getUsername()).getToken()) //getting token after passing the username
                        .build(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Error in User service",HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error in User service",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
