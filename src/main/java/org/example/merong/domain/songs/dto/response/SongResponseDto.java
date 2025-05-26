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
        private final Long viewCount;

        public Get(Song song, Long viewCount) {
            this.id = song.getId();
            this.userId = song.getUser() != null ? song.getUser().getId() : null;
            this.name = song.getTitle();
            this.singer = song.getSinger();
            this.genre = song.getGenre();
            this.createdAt = song.getCreatedAt();
            this.updatedAt = song.getUpdatedAt();
            this.likeCount = song.getLikeCount();
            this.playCount = song.getPlayCount();
            this.description = song.getDescription();
            this.viewCount = viewCount;

            if (song.getComments() != null) {
                this.comments = song.getComments().stream()
                        .map(comment -> new CommentResponseDto.Get(
                                comment.getUser() != null ? comment.getUser().getId() : null,
                                comment.getContent(),
                                comment.getUpdatedAt()
                        ))
                        .toList();
            } else {
                this.comments = List.of();
            }
        }

        public Get(Song song) {
            this(song, 0L);
        }
    }

    @Getter
    public static class Update {
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

    public static Get fromEntity(Song song) {
        return new Get(song);
    }

    public static Get fromEntity(Song song, Long viewCount) {
        return new Get(song, viewCount);
    }
}
