package com.example.web_project.Service.product;

import com.example.web_project.Entity.Brand;
import com.example.web_project.Repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand getBrandById(Long id) {
        Optional<Brand> brandOptional = brandRepository.findById(id);
        return brandOptional.orElse(null);
    }

    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public Brand updateBrand(Long id, Brand newBrand) {
        Optional<Brand> existingBrandOptional = brandRepository.findById(id);
        if (existingBrandOptional.isPresent()) {
            Brand existingBrand = existingBrandOptional.get();
            existingBrand.setBrandName(newBrand.getBrandName());
            return brandRepository.save(existingBrand);
        } else {
            return null;
        }
    }

    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }

    public List<Brand> searchBrands(String keyword) {
        return brandRepository.findByBrandNameContainingIgnoreCase(keyword);
    }
    public Optional<Brand> findById(Long id) {
        return brandRepository.findById(id);
    }
}
