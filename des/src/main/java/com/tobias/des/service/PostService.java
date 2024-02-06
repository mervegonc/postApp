package com.tobias.des.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobias.des.dto.requests.PostCreateRequest;
import com.tobias.des.dto.requests.PostUpdateRequest;
import com.tobias.des.dto.responses.LikeResponse;
import com.tobias.des.dto.responses.PostResponse;
import com.tobias.des.entity.Post;
import com.tobias.des.entity.User;
import com.tobias.des.repository.PostRepository;

@Service

public class PostService {

	private PostRepository postRepository;
	private CustomUserDetailsService userService;
	private LikeService likeService;

	public PostService(PostRepository postRepository, CustomUserDetailsService userService/* ,/*LikeService likeService */) {
		super();
		this.postRepository = postRepository;
		this.userService = userService;
		// this.likeService =likeService;

	}

	@Autowired
	public void setLikeService(LikeService likeService) {
		this.likeService = likeService;
	}

	public List<PostResponse> getAllPosts(Optional<Long> userId) {
		List<Post> list;
		if (userId.isPresent()) {
			list = postRepository.findByUserId(userId.get());
		}
		list = postRepository.findAll();
		return list.stream().map(p -> {
			List<LikeResponse> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null),
					Optional.of(p.getId()));
			return new PostResponse(p, likes);
		}).collect(Collectors.toList());
	}

	public Post getOnePostById(Long postId) {
		return postRepository.findById(postId).orElse(null);
	}

	public Post createOnePost(PostCreateRequest newPostCreateRequest) {
		User user = userService.getOneUserById(newPostCreateRequest.getUserId());
		if (user == null)
			return null;
		Post toSave = new Post();
		toSave.setId(newPostCreateRequest.getId());
		toSave.setText(newPostCreateRequest.getText());
		toSave.setTitle(newPostCreateRequest.getTitle());
		toSave.setUser(user);
		return postRepository.save(toSave);
	}

	public Post updateOnePostById(Long postId, PostUpdateRequest updatePost) {
		Optional<Post> post = postRepository.findById(postId);
		if (post.isPresent()) {
			Post toUpdate = post.get();
			toUpdate.setText(updatePost.getText());
			toUpdate.setTitle(updatePost.getTitle());
			postRepository.save(toUpdate);
			return toUpdate;
		}
		return null;
	}

	public void deleteOnePostById(Long postId) {
		postRepository.deleteById(postId);
	}

}
