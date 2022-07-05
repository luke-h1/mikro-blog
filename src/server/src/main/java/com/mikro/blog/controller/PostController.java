package com.mikro.blog.controller;

import com.mikro.blog.payload.PostDto;
import com.mikro.blog.payload.PostResponse;
import com.mikro.blog.service.PostService;
import com.mikro.blog.utils.AppConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Post controller")
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
  private PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @ApiOperation(value = "Create post")
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
    return new ResponseEntity<PostDto>(
        postService.createPost(postDto),
        HttpStatus.CREATED
    );
  }

  // @PreAuthorize("isAuthenticated()")
  @ApiOperation(value = "Get all posts")
  @GetMapping
  public PostResponse getAllPosts(
    @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
    @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
  ) {
    return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
  }

  @ApiOperation(value = "Get post by id")
  @GetMapping("/{id}")
  public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
    return ResponseEntity.ok(postService.getPostById(id));
  }

  @ApiOperation(value = "Update post")
  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") Long id) {
    return new ResponseEntity<>(
        postService.updatePost(postDto, id),
        HttpStatus.OK
    );
  }

  @ApiOperation(value = "Delete post")
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deletePostById(@PathVariable Long id) {
    postService.deletePostById(id);
    return ResponseEntity.ok("Post deleted successfully");
  }
}
