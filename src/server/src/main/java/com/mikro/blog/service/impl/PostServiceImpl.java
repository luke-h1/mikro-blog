package com.mikro.blog.service.impl;

import com.mikro.blog.entity.Post;
import com.mikro.blog.exception.ResourceNotFoundException;
import com.mikro.blog.payload.PostDto;
import com.mikro.blog.payload.PostResponse;
import com.mikro.blog.repository.PostRepository;
import com.mikro.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

public class PostServiceImpl implements PostService {

  private PostRepository postRepository;

  private ModelMapper mapper;

  public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
    this.postRepository = postRepository;
    this.mapper = mapper;
  }

  @Override
  public PostDto createPost(PostDto postDto) {
    Post post = mapToEntity(postDto);
    Post newPost = postRepository.save(post);
    PostDto postResponse = mapToDto(newPost);
    return postResponse;
  }

  @Override
  public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
    Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

    // create pageable instance
    Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

    Page<Post> posts = postRepository.findAll(pageable);

    // get content from page object
    List<Post> listOfPosts = posts.getContent();

    List<PostDto> content = listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
    PostResponse postResponse = new PostResponse();
    postResponse.setContent(content);
    postResponse.setPageNo(pageNo);
    postResponse.setPageSize(pageSize);
    postResponse.setTotalElements(posts.getTotalElements());
    postResponse.setTotalPages(posts.getTotalPages());
    postResponse.setLast(posts.isLast());
    return postResponse;

  }

  @Override
  public PostDto getPostById(Long id) {
    Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    return mapToDto(post);
  }

  @Override
  public PostDto updatePost(PostDto postDto, Long id) {
    Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

    post.setTitle(postDto.getTitle());
    post.setContent(postDto.getContent());
    post.setDescription(postDto.getDescription());

    Post updatedPost = postRepository.save(post);
    return mapToDto(updatedPost);
  }

  @Override
  public void deletePostById(Long id) {
    Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    postRepository.delete(post);
  }

  private PostDto mapToDto(Post post) {
    PostDto postDto = mapper.map(post, PostDto.class);
    return postDto;
  }

  private Post mapToEntity(PostDto postDto) {
    Post post = mapper.map(postDto, Post.class);
    return post;
  }
}
