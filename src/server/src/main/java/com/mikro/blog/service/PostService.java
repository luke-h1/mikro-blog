package com.mikro.blog.service;

import com.mikro.blog.payload.PostDto;
import com.mikro.blog.payload.PostResponse;

public interface PostService {
  PostDto createPost(PostDto postDto);

  PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

  PostDto getPostById(Long id);

  PostDto updatePost(PostDto postDto, Long id);

  void deletePostById(Long id);
}
