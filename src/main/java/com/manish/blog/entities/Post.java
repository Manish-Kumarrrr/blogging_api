package com.manish.blog.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(name = "post_title" ,length=100 ,nullable = false)
    private String title;


    @Column(length = 10000)
    private String content;

    private String imageName;

    private Date addedDate;

    @JoinColumn(name = "categoryId")
    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy="post",cascade = CascadeType.ALL)
    private Set<Comment> comments=new HashSet<>();


}
