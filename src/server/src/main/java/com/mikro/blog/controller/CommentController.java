package com.mikro.blog.controller;

import com.mikro.blog.payload.CommentDto;
import com.mikro.blog.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "CRUD endpoints for comment resources")
@RestController
@RequestMapping("/api/v1")
public class CommentController {

  private CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @ApiOperation(value = "Create a new comment")
  @PostMapping("/posts/{postId}/comments")
  public ResponseEntity<CommentDto> createComment(@Valid @PathVariable(value = "postId") long postId, @RequestBody CommentDto commentDto) {
    return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
  }

  @ApiOperation(value = "Get all comments")
  @GetMapping("/posts/{postId}/comments")
  public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") Long postId) {
    return commentService.getCommentsByPostId(postId);
  }

  @ApiOperation(value = "Get a comment by id")
  @GetMapping("/posts/{postId}/comments/{commentId}")
  public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") Long postId, @PathVariable(value = "commentId") Long commentId) {
    CommentDto commentDto = commentService.getCommentById(postId, commentId);
    return new ResponseEntity<>(commentDto, HttpStatus.OK);
  }

  @ApiOperation(value = "Update a comment")
  @PutMapping("/posts/{postId}/comments/{commentId}")
  public ResponseEntity<CommentDto> updateComment(@Valid @PathVariable(value = "postId") Long postId, @PathVariable(value = "commentId") Long commentId, @RequestBody CommentDto commentDto) {
    CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);
    return new ResponseEntity<>(updatedComment, HttpStatus.OK);
  }

  @ApiOperation(value = "Delete a comment")
  @DeleteMapping("/posts/{postId}/comments/{commentId}")
  public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId, @PathVariable(value = "commentId") Long commentId) {
    commentService.deleteComment(postId, commentId);
    return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
  }
}
