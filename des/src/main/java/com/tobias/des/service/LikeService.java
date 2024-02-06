package com.tobias.des.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.tobias.des.dto.requests.LikeCreateRequest;
import com.tobias.des.dto.responses.LikeResponse;
import com.tobias.des.entity.Like;
import com.tobias.des.entity.Post;
import com.tobias.des.entity.User;
import com.tobias.des.repository.LikeRepository;

@Service
@Lazy
public class LikeService {

	private LikeRepository likeRepository;
	private CustomUserDetailsService userService;
	private PostService postService;

	public LikeService(LikeRepository likeRepository, CustomUserDetailsService userService, PostService postService) {
		super();
		this.likeRepository = likeRepository;
		this.userService = userService;
		this.postService = postService;
	}

	public Like createOneLike(LikeCreateRequest request) {
		User user = userService.getOneUserById(request.getUserId());
		Post post = postService.getOnePostById(request.getPostId());
		if (user != null && post != null) {
			Like likeToSave = new Like();
			likeToSave.setId(request.getId());
			likeToSave.setPost(post);
			likeToSave.setUser(user);
			return likeRepository.save(likeToSave);
		} else
			return null;
	}

	public Like getOneLikeById(Long likeId) {
		return likeRepository.findById(likeId).orElse(null);
	}

	public void deleteOneLikeById(Long likeId) {
		likeRepository.deleteById(likeId);

	}

	public List<LikeResponse> getAllLikesWithParam(Optional<Long> userId, Optional<Long> postId) {
		List<Like> list;
		if (userId.isPresent() && postId.isPresent()) {
			list = likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
		} else if (userId.isPresent()) {
			list = likeRepository.findByUserId(userId.get());
		} else if (postId.isPresent()) {
			list = likeRepository.findByPostId(postId.get());
		} else
			list = likeRepository.findAll();
		return list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
	}

}
