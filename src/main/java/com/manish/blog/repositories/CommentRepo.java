package com.manish.blog.repositories;

import com.manish.blog.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.*;

public interface CommentRepo extends JpaRepository<Comment,Integer> {
}
