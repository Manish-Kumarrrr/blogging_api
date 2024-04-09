package com.manish.blog.services.impl;

import com.manish.blog.entities.Comment;
import com.manish.blog.entities.Post;
import com.manish.blog.exceptions.ResourceNotFoundException;
import com.manish.blog.payloads.CommentDto;
import com.manish.blog.repositories.CommentRepo;
import com.manish.blog.repositories.PostRepo;
import com.manish.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","post id",postId));
        Comment comment=this.modelMapper.map(commentDto,Comment.class);
        comment.setPost(post);
        Comment newComment=this.commentRepo.save(comment);
        return this.modelMapper.map(newComment,CommentDto.class);

    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment","commentId",commentId));
        this.commentRepo.delete(comment);

    }
}
