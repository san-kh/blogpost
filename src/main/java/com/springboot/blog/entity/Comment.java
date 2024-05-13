package com.springboot.blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity(name = "comments")
public class Comment {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY ) 
private Long id;

private String name;
private String email;
private String body;

//FetchType.LAZY  tells hibernate to only fetch the related entities from the database 
//when u use the relationship.
@ManyToOne(fetch = FetchType.LAZY)
//@JoinColumn defines the forein key column 
@JoinColumn(name = "post_id",nullable = false)
private Posts post;


}
