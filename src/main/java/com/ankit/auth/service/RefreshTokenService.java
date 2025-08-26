package com.ankit.auth.service;

import com.ankit.auth.entities.RefreshToken;
import com.ankit.auth.entities.UserInfo;
import com.ankit.auth.repo.RefreshTokenRepo;
import com.ankit.auth.repo.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private UserInfoRepo userInfoRepo;

    @Autowired
    private RefreshTokenRepo refreshTokenRepo;

    public RefreshToken createRefreshToken(String username) {
        UserInfo extractedUserInfo = userInfoRepo.findByUsername(username);
        RefreshToken createdRefreshToken = RefreshToken.builder()
                .userInfo(extractedUserInfo)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();
        return refreshTokenRepo.save(createdRefreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if(refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepo.delete(refreshToken);
            throw new RuntimeException(refreshToken.getToken() + "Refresh token is expired, please login again");
        }

        return refreshToken;
    }
}
