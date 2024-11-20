package com.example.web_project.Controller;
import com.example.web_project.Entity.Brand;
import com.example.web_project.Entity.Category;
import com.example.web_project.Repository.BrandRepository;
import com.example.web_project.Repository.CategoryRepository;
import com.example.web_project.Repository.ProductRepository;
import com.example.web_project.Service.ResourceNotFoundException;
import com.example.web_project.Service.product.BrandService;
import com.example.web_project.Service.product.CategoryService;
import com.example.web_project.Service.product.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.example.web_project.Entity.Product;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }



    @GetMapping("/{productReference}")
    public ResponseEntity<Product> getProductByReference(@PathVariable("productReference") String productReference) {
        Product product = productService.getProductByReference(productReference);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }





    @GetMapping("/{productReference}/category")
    public ResponseEntity<Category> getCategoryByProductReference(@PathVariable String productReference) {
        Category category = productService.getCategoryByReference(productReference);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{productReference}/brand")
    public ResponseEntity<Brand> getBrandByProductReference(@PathVariable String productReference) {
        Brand brand = productService.getBrandByReference(productReference);
        return brand != null ? ResponseEntity.ok(brand) : ResponseEntity.notFound().build();
    }
    @PostMapping("/compare/add")
    public ResponseEntity<String> addToCompare(@RequestBody Product product) {
        boolean added = productService.addToCompare(product);
        if (added) {
            return ResponseEntity.ok("Product added to comparison list successfully");
        } else {
            return ResponseEntity.badRequest().body("Product already exists in comparison list");
        }
    }

    @GetMapping("/compare/check/{productId}")
    public ResponseEntity<Boolean> hasProductInCompare(@PathVariable Long productId) {
        boolean exists = productService.hasProductInCompare(productId);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/compare/list")
    public ResponseEntity<List<Product>> getCompareProducts() {
        List<Product> compareProducts = productService.getCompareProducts();
        return ResponseEntity.ok(compareProducts);
    }

    @DeleteMapping("/compare/{productId}")
    public ResponseEntity<Void> removeFromCompare(@PathVariable String productId) {
        productService.removeFromCompare(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/compare")
    public ResponseEntity<List<Product>> getComparedProducts() {
        List<Product> comparedProducts = productService.getCompareProducts();
        if (comparedProducts != null && !comparedProducts.isEmpty()) {
            return ResponseEntity.ok(comparedProducts);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping("/add")
    public Product create(@RequestParam(value ="file", required = false) MultipartFile file,
                          @RequestParam(value ="id", required = false) String id,
                          @RequestParam(value ="name", required = false) String name,
                          @RequestParam(value ="description", required = false) String description,
                          @RequestParam(value ="price", required = false) double price,
                          @RequestParam(value ="color", required = false) String color,
                          @RequestParam(value ="quantity", required = false) long quantity,
                          @RequestParam(value ="brandId", required = false) long brandId,
                          @RequestParam(value ="categoryId", required = false) long categoryId


    ) {
        try {
            Product product = new Product();
            product.setName(name);
            product.setProductReference(id);
            product.setDescription(description);
            product.setPrice(price);
            product.setColor(color);
            product.setQuantity(quantity);

            String timestamp = Long.toString(System.currentTimeMillis());

            String destinationPath = "C:\\Users\\manou\\IdeaProjects\\ProjetWeb\\front\\ecommerce-sophia-new\\src\\assets\\images\\products\\";
            String newFilename = timestamp + "_" + file.getOriginalFilename();

            file.transferTo(new File(destinationPath + newFilename));
            String newOwnerImagePath = "products\\" + newFilename;

            product.setImage(newOwnerImagePath);
            Brand projectOwner = brandService.findById(brandId)
                    .orElseThrow(() -> new ResourceNotFoundException("ProjectOwner not found with id: " + brandId));
            product.setBrand(projectOwner);
            Category project = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("ProjectOwner not found with id: " + categoryId));
            product.setCategory(project);

            return productRepository.save(product);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    @PutMapping("/update/{productReference}")
    public ResponseEntity<Product> updateProduct(@PathVariable("productReference") String productReference,
                                                 @RequestParam(value ="file", required = false) MultipartFile file,
                                                 @RequestParam(value ="id", required = false) String id,
                                                 @RequestParam(value ="name", required = false) String name,
                                                 @RequestParam(value ="description", required = false) String description,
                                                 @RequestParam(value ="price", required = false) double price,
                                                 @RequestParam(value ="color", required = false) String color,
                                                 @RequestParam(value ="quantity", required = false) long quantity,
                                                 @RequestParam(value ="brandId", required = false) long brandId,
                                                 @RequestParam(value ="categoryId", required = false) long categoryId) {
        try {
            Product existingProduct = productService.getProductByReference(productReference);
            if (existingProduct != null) {
                existingProduct.setName(name);
                existingProduct.setDescription(description);
                existingProduct.setPrice(price);
                existingProduct.setColor(color);
                existingProduct.setQuantity(quantity);

                if (file != null && !file.isEmpty()) {
                    String timestamp = Long.toString(System.currentTimeMillis());

                    String destinationPath = "C:\\Users\\manou\\IdeaProjects\\ProjetWeb\\front\\ecommerce-sophia-new\\src\\assets\\images\\products\\";

                    String newFilename = timestamp + "_" + file.getOriginalFilename();

                    file.transferTo(new File(destinationPath + newFilename));
                    String newOwnerImagePath = "products\\" + newFilename;

                    existingProduct.setImage(newOwnerImagePath);
                }

                Brand projectOwner = brandService.findById(brandId)
                        .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + brandId));
                existingProduct.setBrand(projectOwner);
                Category project = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
                existingProduct.setCategory(project);

                Product savedProduct = productService.saveProduct(existingProduct);

                return ResponseEntity.ok(savedProduct);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/delete/{productReference}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productReference") String productReference) {
        Product existingProduct = productService.getProductByReference(productReference);
        if (existingProduct != null) {
            productService.deleteProduct(productReference);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> searchResults = productService.searchProducts(keyword);
        return ResponseEntity.ok(searchResults);
    }

}