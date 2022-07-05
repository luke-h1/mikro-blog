package com.mikro.blog.payload;

import lombok.Data;

@Data
public class SignupDto {
  private String firstName;
  private String lastName;
  private String username;
  private String email;
  private String password;
}
