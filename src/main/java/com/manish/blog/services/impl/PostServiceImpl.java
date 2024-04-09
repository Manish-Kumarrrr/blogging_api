package com.manish.blog.services.impl;

import com.manish.blog.entities.Category;
import com.manish.blog.entities.Post;
import com.manish.blog.entities.User;
import com.manish.blog.exceptions.ResourceNotFoundException;
import com.manish.blog.payloads.PostDto;
import com.manish.blog.payloads.PostResponse;
import com.manish.blog.repositories.CategoryRepo;
import com.manish.blog.repositories.PostRepo;
import com.manish.blog.repositories.UserRepo;
import com.manish.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;


    // create post
    @Override
    public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
        Post post=this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());

        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","userId",userId));
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","Id",categoryId));

        post.setCategory(category);
        post.setUser(user);

        Post newPost=this.postRepo.save(post);
        return this.modelMapper.map(newPost,PostDto.class);
    }


    // update post
    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","postId",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        this.postRepo.save(post);

        return this.modelMapper.map(post,PostDto.class) ;
    }


    // delete post
    @Override
    public void deletePost(Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","postId",postId));
        this.postRepo.delete(post);


    }

    // get all post
    @Override
    public PostResponse getAllPost(Integer pageNumber , Integer pageSize,String sortBy,String sortDir) {
        Sort sort=null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).ascending();
        }
        else
            sort=Sort.by(sortBy).descending();

        // ternary
        Sort sort1=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

//        Pageable pageable =PageRequest.of(pageNumber,pageSize, Sort.by(sortBy).descending());// org.springframework.data.domain
        Pageable pageable =PageRequest.of(pageNumber,pageSize, sort);// org.springframework.data.domain

        Page<Post> pagePost=this.postRepo.findAll(pageable);
        List<Post> allPosts=pagePost.getContent();
        List<PostDto> postDtos=allPosts.stream().map(post -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }


    // get single post
    @Override
    public PostDto getPostById(Integer Id) {

        Post posts =this.postRepo.findById(Id).orElseThrow(()-> new ResourceNotFoundException("post","PostId",Id));

        return this.modelMapper.map(posts,PostDto.class);
    }


    // get post by category
//    @Override
//    public List<PostDto> getPostByCategory(Integer categoryId) {
//        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","id",categoryId));
//
//        List<Post> posts=this.postRepo.findByCategory(category);
//        List<PostDto> postDtos= posts.stream().map(post -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
//
//        return postDtos;
//    }

    // get by category after pagination
    @Override
    public PostResponse getPostByCategory(Integer pageNumber, Integer pageSize, Integer categoryId) {
        Pageable pageable=PageRequest.of(pageNumber,pageSize);
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","id",categoryId));

        Page<Post> posts=this.postRepo.findByCategory(category,pageable);
        List<Post> allPost=posts.getContent();
        List<PostDto> postDtos= allPost.stream().map(post -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse(
                postDtos,
                posts.getNumber(),
                posts.getSize(),
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.isLast()
        );
        return postResponse;
    }


    // get post by user
//    @Override
//    public List<PostDto> getPostByUser(Integer userId) {
//
//        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","userId",userId));
//        List<Post> posts=this.postRepo.findByUser(user);
//        List<PostDto> postDtos=posts.stream().map(post->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
//        return postDtos;
//    }


    // get post by user after pagination
    @Override
    public PostResponse getPostByUser(Integer pageNumber, Integer pageSize, Integer userId) {

        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","userId",userId));
        Pageable pageable=PageRequest.of(pageNumber,pageSize);
        Page<Post> posts=this.postRepo.findByUser(user,pageable);
        List<Post> allPost=posts.getContent();
        List<PostDto> postDtos=allPost.stream().map(post->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setPageSize(posts.getSize());
        postResponse.setPageNumber(posts.getNumber());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLastPage(posts.isLast());
        return postResponse;
    }


    // search
    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts=this.postRepo.findByTitleContaining(keyword);
        List<PostDto> postDtos=posts.stream().map(post-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
}
