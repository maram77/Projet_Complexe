package com.example.web_project.Service.blog;

import com.example.web_project.Entity.Blog;
import com.example.web_project.Entity.Comment;
import com.example.web_project.Entity.User;
import com.example.web_project.Repository.BlogRepository;
import com.example.web_project.Repository.CommentRepository;
import com.example.web_project.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private UserRepository userRepository;
    public List<Comment> getCommentsByBlogId(Long id){
        return commentRepository.findAllByBlog_Id(id);

    }

    public Comment createComment(Long blogId,Long userId,String content) {
        Optional<Blog> blogOptional = blogRepository.findById(blogId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (blogOptional.isPresent() && userOptional.isPresent()) {
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setUser(userOptional.get());
            comment.setBlog(blogOptional.get());
            comment.setCreated_on(new Date());
            return  commentRepository.save(comment);
        }
            throw new EntityNotFoundException("Blog not found");
    }
    public Comment updateComment(Long id,String updatedContent) {
        Optional<Comment> CommentOptional = commentRepository.findById(id);
        if (CommentOptional.isPresent()) {
           Comment Comment = CommentOptional.get();
           Comment.setBlog(CommentOptional.get().getBlog());
           Comment.setUser(CommentOptional.get().getUser());
           Comment.setContent(updatedContent);
           Comment.setCreated_on(new Date());
            return commentRepository.save(Comment);
        } else {
            return null;
        }
    }
    public boolean deleteComment(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
