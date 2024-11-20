package com.example.web_project.Repository;

import com.example.web_project.Entity.User;
import com.example.web_project.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByEmail(String email);

    User findByUserRole(UserRole userRole);

    Optional<User> findByEmail(String email);

}
