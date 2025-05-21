package org.example.merong.domain.searches.dto.response;

import lombok.Getter;
import org.example.merong.domain.songs.entity.Song;
import org.example.merong.domain.songs.enums.Genres;

import java.time.LocalDateTime;

@Getter
public class SearchResponseDto {

    private final String title;
    private final String singer;
    private final Genres genre;
    private final LocalDateTime searchedAt;
    private final String description;

    public SearchResponseDto(Song song) {
        this.title = song.getTitle();
        this.singer = song.getSinger();
        this.genre = song.getGenre();
        this.searchedAt = LocalDateTime.now();
        this.description = song.getDescription();
    }
}
