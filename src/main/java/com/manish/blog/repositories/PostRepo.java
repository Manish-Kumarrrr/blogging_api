package com.manish.blog.repositories;

import com.manish.blog.entities.Category;
import com.manish.blog.entities.Post;
import com.manish.blog.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {


//    List<Post> findByUser(User user);
    Page<Post> findByUser(User user,Pageable pageable);
//    List<Post> findByCategory(Category category);
    Page<Post> findByCategory(Category category, Pageable pageable);

//    @Query("select p from Post p where p.title like :key") // video 26 %key%
    List<Post> findByTitleContaining(String title);
}
