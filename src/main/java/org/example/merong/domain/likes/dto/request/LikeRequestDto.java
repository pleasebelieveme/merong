package org.example.merong.domain.likes.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeRequestDto {

	private final Long userId;
	private final Long songId;

}
