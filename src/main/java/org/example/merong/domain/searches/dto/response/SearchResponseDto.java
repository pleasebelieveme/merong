package org.example.merong.domain.searches.dto.response;

import lombok.Getter;
import org.example.merong.domain.songs.entity.Song;
import org.example.merong.domain.songs.enums.Genres;

import java.time.LocalDateTime;

@Getter
public class SearchResponseDto {

    private String title;
    private String singer;
    private Genres genre;
    private LocalDateTime createdAt;
    private String description;

    public SearchResponseDto(Song song) {
        this.title = song.getTitle();
        this.singer = song.getSinger();
        this.genre = song.getGenre();
        this.createdAt = song.getCreatedAt();
        this.description = song.getDescription();
    }
}
