package com.example.web_project.Service.blog;

import com.example.web_project.Entity.Blog;
import com.example.web_project.Repository.BlogRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public List<Blog> getAllBlogs() {

        return blogRepository.findAll();
    }

    public Blog getBlogById(Long id) {
        Optional<Blog> blogOptional = blogRepository.findById(id);
        if(blogOptional.isPresent()){
            Blog blog = blogOptional.get();
            blog.setCountView(blog.getCountView()+1);

            return  blogRepository.save(blog);
        }else {
            throw new EntityNotFoundException("Blog not found !");
        }
    }

    public List<Blog> searchByTitle(String name){
        return blogRepository.findAllByTitle(name);
    }

    public Blog createBlog(Blog blog){
        blog.setCreated_on(new Date());
        blog.setCountView(0);
        blog.setCountLike(0);
        return blogRepository.save(blog);
    }
    public void uploadImage(Long id, MultipartFile imageFile) throws IOException {
        Optional<Blog> blogOptional = blogRepository.findById(id);
        if(blogOptional.isPresent() && !imageFile.isEmpty()) {
            // Convert MultipartFile to byte array
            byte[] image = imageFile.getBytes();
            Blog blog = blogOptional.get();
            // Set image  in blog entity
            blog.setImage(image);
            // Save blog entity with updated image
            blogRepository.save(blog);
        } else {
            throw new IllegalArgumentException("Blog not found or image file is empty.");
        }
    }
    public byte[] getBlogImageById(Long id) {
        Optional<Blog> blogOptional = blogRepository.findById(id);
        if(blogOptional.isPresent()){
            Blog blog = blogOptional.get();
            // Assuming 'image' is the attribute containing the BLOB data
            byte[] image = blog.getImage();
            return image;
        } else {
            return null;
        }
    }
    public Blog updateBlog(Long id, Blog updatedBlog) throws IOException{
        Optional<Blog> blogOptional = blogRepository.findById(id);
        if (blogOptional.isPresent()) {
            Blog blog = blogOptional.get();
            blog.setTitle(updatedBlog.getTitle());
            blog.setDescription(updatedBlog.getDescription());
            blog.setImage(updatedBlog.getImage());
            blog.setUser(blogOptional.get().getUser());
            blog.setUpdated_on(new Date());
            return blogRepository.save(blog);
        } else {
            return null;
        }
    }

    public boolean deleteBlog(Long id) {
        if (blogRepository.existsById(id)) {
            blogRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public void likeBlog(Long id){
        Optional<Blog> blogOptional = blogRepository.findById(id);
        if (blogOptional.isPresent()) {
            Blog blog = blogOptional.get();
            blog.setCountLike(blog.getCountLike()+1);
            blogRepository.save(blog);
        } else {
            throw new EntityNotFoundException("Blog not found with id "+ id);
        }
    }

}
