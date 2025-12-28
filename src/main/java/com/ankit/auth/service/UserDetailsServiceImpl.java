package com.ankit.auth.service;

import com.ankit.auth.dto.UserInfoDto;
import com.ankit.auth.entities.UserInfo;
import com.ankit.auth.repo.UserInfoRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Component
@Data
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserInfoRepo userInfoRepo;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserValidationService userValidation;

    public UserDetailsServiceImpl(UserInfoRepo userInfoRepo, PasswordEncoder passwordEncoder) {
        this.userInfoRepo = userInfoRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userInfoRepo.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("Username could not found !!");
        }
        return new CustomUserInfo(user);
    }

    public UserInfo checkIfUserAlreadyExists(UserInfoDto userInfoDto) {
        return userInfoRepo.findByUsername(userInfoDto.getUsername());
    }

    public Boolean signUpUser(UserInfoDto userInfoDto) {
//        if(userValidation.validateUserDetails(userInfoDto)) {
            userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
            //user already exists
            if (Objects.nonNull(checkIfUserAlreadyExists(userInfoDto))) {
                return false;
            }
            String userId = UUID.randomUUID().toString();
            userInfoRepo.save(new UserInfo(userId, userInfoDto.getUsername(), userInfoDto.getPassword(), new HashSet<>()));
            return true;
//        } else {
//            return false;
//        }
    }
}
