package com.mikro.blog.payload;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@ApiModel(value = "post model")
@Data
public class PostDto {
  private Long id;

  @NotEmpty(message = "Title is required")
  @Size(min = 2, message="Title must be at least 2 characters")
  private String title;

  @NotEmpty(message = "Description is required")
  @Size(min = 10, message="Description must be at least 10 characters")
  private String description;

  @NotEmpty(message = "Content is required")
  private String content;

  private Set<CommentDto> comments;
}
