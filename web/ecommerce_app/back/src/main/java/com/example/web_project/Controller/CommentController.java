package com.example.web_project.Controller;


import com.example.web_project.Entity.Comment;
import com.example.web_project.Service.blog.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/Comments")
@CrossOrigin(origins = "*")
public class CommentController {
    @Autowired
    private CommentService commentService;



    @GetMapping("/{blogId}")
    public ResponseEntity<?> getCommentsByBlogId(@PathVariable Long blogId) {
        try{
            List<Comment> comments = commentService.getCommentsByBlogId(blogId);
            return new ResponseEntity<>(comments, HttpStatus.OK);
        }catch(Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Wrong !");
        }


    }

    @PostMapping
    public ResponseEntity<?> createComment(@RequestParam Long blogId,@RequestParam Long userId, @RequestBody String content) {
        try{
            return  ResponseEntity.ok(commentService.createComment(blogId,userId,content));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody String updatedContent) {
        Comment comment = commentService.updateComment(id, updatedContent);
        if (comment != null) {
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        boolean deleted = commentService.deleteComment(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
