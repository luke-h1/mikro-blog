package com.mikro.blog.service;

import com.mikro.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
  CommentDto createComment(Long postId, CommentDto commentDto);

  List<CommentDto> getCommentsByPostId(Long postId);

  CommentDto getCommentById(Long postId, Long commentId);

  CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest);

  void deleteComment(Long commentId, Long postId);
}
