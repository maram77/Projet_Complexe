package com.example.web_project.Dto;

import com.example.web_project.enums.UserRole;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String telephone;
    private String password;
    private UserRole userRole;


}
