package org.example.merong.domain.songs.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.merong.domain.songs.enums.Genres;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SongResponseDto {

    /*
     PK
     유저 PK
     노래 제목
     가수
     장르
     작성일(발매일)
     수정일
     좋아요 수
     재생 수
     설명
     */

    private final Long id;

    private final Long userId;

    private final String name;

    private final String singer;

    private final Genres genre;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    private final Long likeCount;

    private final Long playCount;

    private final String description;




}
