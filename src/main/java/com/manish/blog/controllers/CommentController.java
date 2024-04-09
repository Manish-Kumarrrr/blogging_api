package com.manish.blog.controllers;


import com.manish.blog.payloads.ApiResponse;
import com.manish.blog.payloads.CommentDto;
import com.manish.blog.services.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentServiceImpl commentService;

    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable Integer postId,
                                                    @RequestBody CommentDto commentDto){
        CommentDto commentDto1=this.commentService.createComment(commentDto,postId);
        return new ResponseEntity<>(commentDto1, HttpStatus.CREATED);

    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("comment deleted successfully!!",true),HttpStatus.OK);
    }

}
