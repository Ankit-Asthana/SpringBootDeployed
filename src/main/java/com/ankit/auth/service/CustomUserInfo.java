package com.ankit.auth.service;

import com.ankit.auth.entities.Role;
import com.ankit.auth.entities.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


//This class is used to store the user-details of a user for spring security.
public class CustomUserInfo extends UserInfo implements UserDetails {

    private String username;
    private String password;
    Collection<? extends GrantedAuthority> authorities;

    CustomUserInfo(UserInfo byUsername) {
        this.username = byUsername.getUserName();
        this.password = byUsername.getPassword();

        List<GrantedAuthority> auths = new ArrayList<>();
        for(Role roles : byUsername.getUserRoles()) {
            auths.add(new SimpleGrantedAuthority(roles.getRoleName().toUpperCase()));
        }
        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
