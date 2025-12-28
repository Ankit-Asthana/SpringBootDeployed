package com.ankit.auth.dto;

import com.ankit.auth.entities.UserInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserInfoDto extends UserInfo {
    private String username;
    private String lastname;
    private Long phoneNumber;
    private String email;
}
