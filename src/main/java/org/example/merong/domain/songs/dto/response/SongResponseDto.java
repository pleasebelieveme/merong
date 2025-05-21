package org.example.merong.domain.songs.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.merong.domain.comments.dto.response.CommentResponseDto;
import org.example.merong.domain.songs.entity.Song;
import org.example.merong.domain.songs.enums.Genres;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class SongResponseDto {

    @Getter
    public static class Create {

    /*
     PK
     유저 PK
     노래 제목
     가수
     장르
     작성일(발매일)
     설명
     */

    private final Long id;
    private final Long userId;
    private final String name;
    private final String singer;
    private final Genres genre;
    private final LocalDateTime createdAt;
    private final String description;

    public Create(Song song) {
        this.id = song.getId();
        this.userId = song.getUser().getId();
        this.name = song.getTitle();
        this.singer = song.getSinger();
        this.genre = song.getGenre();
        this.createdAt = song.getCreatedAt();
        this.description = song.getDescription();
    }

    }

    @Getter
    public static class Get {

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
        댓글 목록
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
        private final List<CommentResponseDto.Get> comments;

        public Get(Song song) {
            this.id = song.getId();
            this.userId = song.getUser().getId();
            this.name = song.getTitle();
            this.singer = song.getSinger();
            this.genre = song.getGenre();
            this.createdAt = song.getCreatedAt();
            this.updatedAt = song.getUpdatedAt();
            this.likeCount = song.getLikeCount();
            this.playCount = song.getPlayCount();
            this.description = song.getDescription();
            this.comments = song.getComments().stream()
                    .map(comment -> new CommentResponseDto.Get(
                            comment.getUser().getId(),
                            comment.getContent(),
                            comment.getUpdatedAt()
                    )).toList();
        }
    }

    @Getter
    public static class Update {

        /* PK
        유저 PK
        노래 제목
        가수
        장르
        작성일
        수정일
        설명
         */

        private final Long id;
        private final Long userId;
        private final String name;
        private final String singer;
        private final Genres genre;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;
        private final String description;

        public Update(Song song) {
            this.id = song.getId();
            this.userId = song.getUser().getId();
            this.name = song.getTitle();
            this.singer = song.getSinger();
            this.genre = song.getGenre();
            this.createdAt = song.getCreatedAt();
            this.updatedAt = song.getUpdatedAt();
            this.description = song.getDescription();
        }
    }
}
