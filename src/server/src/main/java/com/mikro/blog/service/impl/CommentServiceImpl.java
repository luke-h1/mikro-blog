package com.mikro.blog.service.impl;

import com.mikro.blog.entity.Comment;
import com.mikro.blog.entity.Post;
import com.mikro.blog.exception.BlogAPIException;
import com.mikro.blog.exception.ResourceNotFoundException;
import com.mikro.blog.payload.CommentDto;
import com.mikro.blog.repository.CommentRepository;
import com.mikro.blog.repository.PostRepository;
import com.mikro.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

public class CommentServiceImpl implements CommentService {

  private CommentRepository commentRepository;

  private PostRepository postRepository;

  private ModelMapper mapper;

  public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
    this.commentRepository = commentRepository;
    this.postRepository = postRepository;
    this.mapper = mapper;
  }

  @Override
  public CommentDto createComment(Long postId, CommentDto commentDto) {
    Comment comment = mapToEntity(commentDto);
    Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

    // set post to comment
    comment.setPost(post);

    // save comment entity to DB
    Comment newComment = commentRepository.save(comment);

    return mapToDto(newComment);
  }

  @Override
  public List<CommentDto> getCommentsByPostId(Long postId) {
    List<Comment> comments = commentRepository.findByPostId(postId);

    return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
  }

  @Override
  public CommentDto getCommentById(Long postId, Long commentId) {
    Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

    Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

    if (!comment.getPost().getId().equals(post.getId())) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to this post");
    }
    return mapToDto(comment);
  }

  @Override
  public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) {
    Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

    Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

    if (!comment.getPost().getId().equals(post.getId())) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to this post");
    }

    // TODO: check permissions are correct here
    comment.setName(commentRequest.getName());
    comment.setEmail(commentRequest.getEmail());
    comment.setBody(commentRequest.getBody());

    Comment updatedComment = commentRepository.save(comment);

    return mapToDto(updatedComment);
  }

  @Override
  public void deleteComment(Long commentId, Long postId) {
    Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

    Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

    if (!comment.getPost().getId().equals(post.getId())) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to this post");
    }
    commentRepository.delete(comment);
  }
  private CommentDto mapToDto(Comment comment) {
    CommentDto commentDto = mapper.map(comment, CommentDto.class);
    return commentDto;
  }

  private Comment mapToEntity(CommentDto commentDto) {
    Comment comment = mapper.map(commentDto, Comment.class);
    return comment;
  }
}
