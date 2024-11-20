package com.example.web_project.Security;

import com.example.web_project.Dto.UserDTO;
import com.example.web_project.Entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;
import java.util.Collection;

@Data
public class UserRegistrationDetails implements UserDetails {

    private String userName;
    private String password;
    private boolean isEnabled;
    private List<GrantedAuthority> authorities;

    public UserRegistrationDetails(User user) {
        this.userName = user.getEmail();
        this.password = user.getPassword();
        this.isEnabled = user.isEnabled();
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getUserRole().toString()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}