package org.example.merong.domain.likes.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class LikeRequestDto {

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Add {

		private Long userId;
		private Long songId;
	}
}
