package com.ankit.auth.service;


import com.ankit.auth.dto.UserInfoDto;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserValidationService {

    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^[A-Za-z][A-Za-z0-9._]{6,19}$");

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,64}$");


    public Boolean validateUserDetails(UserInfoDto userInfoDto) {
        if(!userInfoDto.getUsername().isEmpty() && !userInfoDto.getPassword().isEmpty()) {
             if(USERNAME_PATTERN.matcher(userInfoDto.getUsername()).matches() &&
             PASSWORD_PATTERN.matcher(userInfoDto.getPassword()).matches()) {
                 return true;
             } else {
                 return false;
             }
        }
        return false;
    }
}
