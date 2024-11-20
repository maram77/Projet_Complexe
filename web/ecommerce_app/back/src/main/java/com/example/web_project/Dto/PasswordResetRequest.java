package com.example.web_project.Dto;

import lombok.Data;

@Data
public class PasswordResetRequest {
    private String email;

    private String newPassword;
}
