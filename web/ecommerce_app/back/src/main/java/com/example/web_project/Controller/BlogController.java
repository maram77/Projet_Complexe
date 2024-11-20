package com.example.web_project.Controller;

import com.example.web_project.Entity.Blog;
import com.example.web_project.Entity.User;
import com.example.web_project.Repository.UserRepository;
import com.example.web_project.Service.blog.BlogService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/Blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private UserRepository userRepository;
    @GetMapping
    public ResponseEntity<List<Blog>> getAllBlogs() {
        List<Blog> blogs = blogService.getAllBlogs();
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable Long id) {
        Blog blog = blogService.getBlogById(id);
        if (blog != null) {
            return new ResponseEntity<>(blog, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
   @GetMapping("/search/{title}")
    public ResponseEntity<?>  searchByTitle(@PathVariable String title){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(blogService.searchByTitle(title));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getBlogImageById(@PathVariable("id") Long id) {
        byte[] image = blogService.getBlogImageById(id);
        if (image != null && image.length > 0) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(image.length);
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/{id}/image")
    public ResponseEntity<Void> uploadImage(@PathVariable("id") Long id, @RequestParam("image") MultipartFile imageFile) {
        try {
            blogService.uploadImage(id, imageFile);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping
    public ResponseEntity<?> createBlog(@RequestBody Blog blog) {
        try {
            Optional<User> userOptional = userRepository.findById(blog.getUser().getId());
            if (userOptional.isPresent()) {
                blog.setUser(userOptional.get());

                Blog createdBlog = blogService.createBlog(blog);
                return new ResponseEntity<>(createdBlog, HttpStatus.CREATED);

            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable("id") Long id,@RequestBody Blog updatedBlog) throws IOException {
        Blog blog = blogService.updateBlog(id,updatedBlog);
        if (blog != null) {
            return new ResponseEntity<>(blog, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        boolean deleted = blogService.deleteBlog(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/like")
    public ResponseEntity<?> likeBlog(@PathVariable Long id){
        try{
            blogService.likeBlog(id);
            return ResponseEntity.ok(new String[]{"Blog liked successfully ."});
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
