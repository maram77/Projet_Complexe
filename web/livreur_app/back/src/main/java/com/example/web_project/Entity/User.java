package com.example.web_project.Entity;
import com.example.web_project.Dto.UserDTO;
import com.example.web_project.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    @NaturalId(mutable = true)
    private String email;
    private String telephone;
    private String password;
    private UserRole userRole;
    private boolean isEnabled = false;




}
