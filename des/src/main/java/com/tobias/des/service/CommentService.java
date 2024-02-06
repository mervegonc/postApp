package com.tobias.des.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tobias.des.dto.requests.CommentCreateRequest;
import com.tobias.des.dto.requests.CommentUpdateRequest;
import com.tobias.des.entity.Comment;
import com.tobias.des.entity.Post;
import com.tobias.des.entity.User;
import com.tobias.des.repository.CommentRepository;

@Service
public class CommentService {

	private CommentRepository commentRepository;
	private CustomUserDetailsService userService;
	private PostService postService;

	public CommentService(CommentRepository commentRepository, CustomUserDetailsService userService,
			PostService postService) {
		super();
		this.commentRepository = commentRepository;
		this.userService = userService;
		this.postService = postService;
	}

	public List<Comment> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> postId) {
		if (userId.isPresent() && postId.isPresent()) {
			return commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
		} else if (userId.isPresent()) {
			return commentRepository.findByUserId(userId.get());
		} else if (postId.isPresent()) {
			return commentRepository.findByPostId(postId.get());
		} else
			return commentRepository.findAll();

	}

	public Comment getOneCommentById(Long commentId) {
		return commentRepository.findById(commentId).orElse(null);
	}

	public Comment createOneComment(CommentCreateRequest request) {
		User user = userService.getOneUserById(request.getUserId());
		Post post = postService.getOnePostById(request.getPostId());
		if (user != null && post != null) {
			Comment commentToSave = new Comment();
			commentToSave.setId(request.getId());
			commentToSave.setPost(post);
			commentToSave.setUser(user);
			commentToSave.setText(request.getText());
			return commentRepository.save(commentToSave);
		} else
			return null;
	}

	public Comment updateOneCommentById(Long commentId, CommentUpdateRequest request) {
		Optional<Comment> comment = commentRepository.findById(commentId);
		if (comment.isPresent()) {
			Comment commentToUpdate = comment.get();
			commentToUpdate.setText(request.getText());
			return commentRepository.save(commentToUpdate);

		} else
			return null;
	}

	public void deleteOneCommentById(Long commentId) {
		commentRepository.deleteById(commentId);

	}

}
