package com.mikro.blog.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {
  @UniqueConstraint(columnNames = {"username"}),
  @UniqueConstraint(columnNames = {"email"})
})
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
}
