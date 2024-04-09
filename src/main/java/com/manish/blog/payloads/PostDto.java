package com.manish.blog.payloads;

import com.manish.blog.entities.Category;
import com.manish.blog.entities.Comment;
import com.manish.blog.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    private Integer postId;
    private String title;
    private String content;
    private String imageName;

    private UserDto user;
    private CategoryDto category;
    private Date addedDate;
    private Set<CommentDto> comments= new HashSet<>();

}
