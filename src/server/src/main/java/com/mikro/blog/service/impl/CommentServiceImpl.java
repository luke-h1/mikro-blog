package com.mikro.blog.service.impl;

import com.mikro.blog.payload.CommentDto;
import com.mikro.blog.service.CommentService;

import java.util.List;

public class CommentServiceImpl implements CommentService {
  @Override
  public CommentDto createComment(Long postId, CommentDto commentDto) {
    return null;
  }

  @Override
  public List<CommentDto> getCommentsByPostId(Long postId) {
    return null;
  }

  @Override
  public CommentDto getCommentById(Long postId, Long commentId) {
    return null;
  }

  @Override
  public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) {
    return null;
  }

  @Override
  public void deleteComment(Long commentId, Long postId) {

  }
}
