package com.manish.blog.services;

import com.manish.blog.entities.Post;
import com.manish.blog.payloads.PostDto;
import com.manish.blog.payloads.PostResponse;
import org.modelmapper.internal.bytebuddy.description.field.FieldDescription;

import java.util.List;

public interface PostService {

    // create
    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);


    // update
    PostDto updatePost(PostDto postDto,Integer postId);

    // delete
    void deletePost(Integer postId);

    // get all posts
    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);

    // get
    PostDto getPostById(Integer Id);

    // get all post by category
    PostResponse getPostByCategory(Integer pageNumber, Integer pageSize, Integer catgoryId);

    // get all post by user
    PostResponse getPostByUser(Integer pageNumber, Integer pageSize, Integer userId);

    // search posts
    List<PostDto> searchPosts(String keyword);


}
