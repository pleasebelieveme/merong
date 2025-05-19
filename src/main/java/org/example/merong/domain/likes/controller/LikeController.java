package org.example.merong.domain.likes.controller;

import org.example.merong.domain.auth.dto.UserAuth;
import org.example.merong.domain.likes.dto.request.LikeRequestDto;
import org.example.merong.domain.likes.dto.response.LikeResponseDto;
import org.example.merong.domain.likes.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {

	private final LikeService likeService;

	// 좋아요 추가
	@PostMapping("/{songId}")
	public ResponseEntity<LikeResponseDto.Add> doLike(
		@RequestParam Long songId,
		@AuthenticationPrincipal UserAuth userAuth
	){
		LikeResponseDto.Add responseDto = likeService.doLike(songId, userAuth.getId());
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	// 2. 좋아요 취소
	@DeleteMapping("/{songId}")
	public ResponseEntity<Void> unLike(
		@RequestParam Long songId,
		@AuthenticationPrincipal UserAuth userAuth
	) {
		likeService.unLike(songId, userAuth.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
