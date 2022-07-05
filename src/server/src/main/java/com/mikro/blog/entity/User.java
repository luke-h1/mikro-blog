package com.mikro.blog.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private Set<Role> roles;


  @Column(name = "created_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @Column(name = "updated_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;

  @PrePersist
  protected void createdAt() {
    this.createdAt = new Date();
  }

  @PreUpdate
  protected void updatedAt() {
    this.updatedAt = new Date();
  }
}
