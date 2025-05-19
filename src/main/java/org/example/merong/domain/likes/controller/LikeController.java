package org.example.merong.domain.likes.controller;

import org.example.merong.domain.likes.dto.request.LikeRequestDto;
import org.example.merong.domain.likes.dto.response.LikeResponseDto;
import org.example.merong.domain.likes.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {

	private final LikeService likeService;

	// 좋아요 추가
	@PostMapping
	public ResponseEntity<LikeResponseDto> doLike(
		@RequestBody LikeRequestDto dto
	){
		LikeResponseDto responseDto = likeService.doLike(dto);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	// 2. 좋아요 취소
	@DeleteMapping
	public ResponseEntity<Void> unLike(@RequestBody LikeRequestDto requestDto) {
		likeService.unLike(requestDto);
		return ResponseEntity.noContent().build();
	}
}
