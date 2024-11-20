package com.example.web_project.Dto;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String email;
    private String password;
}
