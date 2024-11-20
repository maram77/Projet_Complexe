package com.example.web_project.Controller;

import com.example.web_project.Entity.Brand;
import com.example.web_project.Service.product.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrands() {
        List<Brand> brands = brandService.getAllBrands();
        return ResponseEntity.ok(brands);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable Long id) {
        Brand brand = brandService.getBrandById(id);
        if (brand != null) {
            return ResponseEntity.ok(brand);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Brand> createBrand( @RequestBody Brand brand) {
        Brand savedBrand = brandService.saveBrand(brand);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBrand);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable Long id,@RequestBody Brand brand) {
        Brand updatedBrand = brandService.updateBrand(id, brand);
        if (updatedBrand != null) {
            return ResponseEntity.ok(updatedBrand);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Brand>> searchBrands(@RequestParam String keyword) {
        List<Brand> brands = brandService.searchBrands(keyword);
        return ResponseEntity.ok(brands);
    }
}