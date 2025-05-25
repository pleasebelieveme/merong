package org.example.merong.domain.likes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LikeResponseDto {

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Add {

		private Long id;
		private Long userId;
		private Long songId;
	}
}
