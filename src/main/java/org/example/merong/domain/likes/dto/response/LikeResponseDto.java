package org.example.merong.domain.likes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeResponseDto {

	private final Long id;
	private final Long userId;
	private final Long songId;
}
