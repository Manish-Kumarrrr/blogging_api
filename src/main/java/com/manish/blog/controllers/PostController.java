package com.manish.blog.controllers;

import com.manish.blog.config.AppConstants;
import com.manish.blog.entities.Post;
import com.manish.blog.payloads.ApiResponse;
import com.manish.blog.payloads.PostDto;
import com.manish.blog.payloads.PostResponse;
import com.manish.blog.services.FileService;
import com.manish.blog.services.PostService;
import com.manish.blog.services.impl.FileServiceImpl;
import com.manish.blog.services.impl.PostServiceImpl;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.internal.bytebuddy.description.field.FieldDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    // create
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @PathVariable Integer userId,
            @PathVariable Integer categoryId,
            @RequestBody PostDto postDto
    ){
        PostDto createdPost= postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    // get by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostByUser(
            @PathVariable Integer userId,
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize

            ){
        PostResponse postDtos= postService.getPostByUser(pageNumber,pageSize,userId);
        return  new ResponseEntity<>(postDtos,HttpStatus.OK);
    }

    // get by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostByCategory(
            @PathVariable Integer categoryId,
            @RequestParam(value = "pageNumber" ,defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize" ,defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize
            ){
        PostResponse postDtos=postService.getPostByCategory(pageNumber,pageSize,categoryId);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }

    // get single post
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto postDto=this.postService.getPostById(postId);
        return new ResponseEntity<>(postDto,HttpStatus.FOUND);

    }

    // get all post
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue =AppConstants.PAGE_SIZE ,required = false) Integer pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
            @RequestParam(value="sortDir" , defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
            ){

        PostResponse postResponse =this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    // delete post by id
    @DeleteMapping ("/posts/{postId}")
    public ResponseEntity<ApiResponse>  deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post deleted successfully!!",true),HttpStatus.OK);

    }

    // update post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Integer postId,@RequestBody PostDto postDto){
        PostDto updatedPost=this.postService.updatePost(postDto,postId);
        return new ResponseEntity<>(updatedPost,HttpStatus.OK);
    }


    // search
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keywords){
        List<PostDto> postDtos=this.postService.searchPosts(keywords);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }

    // post image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @PathVariable Integer postId,
            @RequestParam("image")MultipartFile image
            )throws IOException {
        PostDto postDto=this.postService.getPostById(postId);
        String fileName=this.fileService.uploadImage(path,image);

        postDto.setImageName(fileName);
        PostDto updatePost=this.postService.updatePost(postDto,postId);
        return new ResponseEntity<>(updatePost,HttpStatus.OK);

    }

    // method to serve files

    @GetMapping(value="/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public  void serveImage(
            @PathVariable String imageName,
            HttpServletResponse response
    )throws  IOException{
        InputStream resource=this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
