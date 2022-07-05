package com.mikro.blog.service.impl;

import com.mikro.blog.payload.PostDto;
import com.mikro.blog.payload.PostResponse;
import com.mikro.blog.service.PostService;

public class PostServiceImpl implements PostService {
  @Override
  public PostDto createPost(PostDto postDto) {
    return null;
  }

  @Override
  public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
    return null;
  }

  @Override
  public PostDto getPostById(Long id) {
    return null;
  }

  @Override
  public PostDto updatePost(PostDto postDto, Long id) {
    return null;
  }

  @Override
  public void deletePostById(Long id) {

  }
}
