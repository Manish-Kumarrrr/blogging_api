package com.manish.blog.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "comments")
@Setter
@Getter
// no arg constructor is present bydefault
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    private Date date;
//    private boolean edited=false;

    private String content;



    @ManyToOne
    private Post post;
}
