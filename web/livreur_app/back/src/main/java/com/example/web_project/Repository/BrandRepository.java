package com.example.web_project.Repository;

import com.example.web_project.Entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findByBrandNameContainingIgnoreCase(String keyword);
}

