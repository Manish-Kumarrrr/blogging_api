package com.manish.blog.repositories;

import com.manish.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {  // entity , Id data-type
}
