package com.example.web_project.Service.product;

import com.example.web_project.Entity.Brand;
import com.example.web_project.Entity.Category;
import com.example.web_project.Entity.Product;
import com.example.web_project.Repository.ProductRepository;
import com.example.web_project.Service.product.CategoryService;
import com.example.web_project.Service.product.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;
    private List<Product> compareProducts = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(String productReference, Product newProduct) {
        Product existingProduct = productRepository.findByProductReference(productReference);
        if (existingProduct != null) {
            existingProduct.setName(newProduct.getName());
            existingProduct.setDescription(newProduct.getDescription());
            existingProduct.setImage(newProduct.getImage());
            existingProduct.setPrice(newProduct.getPrice());
            existingProduct.setColor(newProduct.getColor());
            existingProduct.setQuantity(newProduct.getQuantity());
            existingProduct.setBrand(newProduct.getBrand());
            existingProduct.setCategory(newProduct.getCategory());
            return productRepository.save(existingProduct);
        } else {
            return null;
        }
    }


    public Product getProductByReference(String productReference) {
        return productRepository.findByProductReference(productReference);
    }

    public Category getCategoryByReference(String productReference) {
        Product product = productRepository.findByProductReference(productReference);
        if (product != null) {
            Long categoryId = product.getCategory().getIdCategory();
            return categoryService.getCategoryById(categoryId);
        } else {
            return null;
        }
    }

    public Brand getBrandByReference(String productReference) {
        Product product = productRepository.findByProductReference(productReference);
        if (product != null) {
            Long brandId = product.getBrand().getIdBrand();
            return brandService.getBrandById(brandId);
        } else {
            return null;
        }
    }
    public boolean addToCompare(Product product) {
        if (compareProducts.contains(product)) {
            return false; // Product already exists in comparison list
        }
        compareProducts.add(product);
        return true; // Product added to comparison list
    }

    // New method to check if a product exists in the comparison list
    public boolean hasProductInCompare(Long productId) {
        for (Product product : compareProducts) {
            if (product.getProductReference().equals(productId)) {
                return true; // Product exists in comparison list
            }
        }
        return false; // Product does not exist in comparison list
    }

    // New method to retrieve the comparison list
    public List<Product> getCompareProducts() {
        return compareProducts;
    }

    public void removeFromCompare(String productId) {
        compareProducts.removeIf(product -> product.getProductReference().equals(productId));
    }
    @Transactional
    public void deleteProduct(String productReference) {
        productRepository.deleteByProductReference(productReference);
    }
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
    }

}