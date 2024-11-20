package com.example.web_project.Repository;

import com.example.web_project.Entity.User;
import com.example.web_project.Entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
    VerificationToken findByUser(User user);

}

