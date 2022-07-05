package com.mikro.blog.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class CommentDto {
  private Long id;

  @NotEmpty(message = "Name cannot be empty")
  private String name;

  @NotEmpty(message = "Email cannot be empty")
  @Email(message = "Email must be valid")
  private String email;

  @NotEmpty(message = "Body cannot be empty")
  @Min(value = 10, message = "Body must have at least 10 characters")
  private String body;
}
