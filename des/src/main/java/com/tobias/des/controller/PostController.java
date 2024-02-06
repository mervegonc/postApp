package com.tobias.des.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tobias.des.dto.requests.PostCreateRequest;
import com.tobias.des.dto.requests.PostUpdateRequest;
import com.tobias.des.dto.responses.PostResponse;
import com.tobias.des.entity.Post;
import com.tobias.des.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	private PostService postService;

	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}

	@GetMapping
	public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId) {
		return postService.getAllPosts(userId);
	}

	@PostMapping
	public Post createOnePost(@RequestBody PostCreateRequest newPostCreateRequest) {
		return postService.createOnePost(newPostCreateRequest);
	}

	@GetMapping("/{postId}")
	public Post getOnePostById(@PathVariable Long postId) {
		return postService.getOnePostById(postId);
	}

	@PutMapping("/{postId}")
	public Post updateOnePostById(@PathVariable Long postId, @RequestBody PostUpdateRequest updatePost) {
		return postService.updateOnePostById(postId, updatePost);
	}

	@DeleteMapping("/{postId}")
	public void deleteOnePostById(@PathVariable Long postId) {
		postService.deleteOnePostById(postId);
	}
}
