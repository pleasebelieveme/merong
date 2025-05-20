package org.example.merong.domain.songs.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.example.merong.domain.songs.enums.Genres;
import org.example.merong.domain.user.entity.User;

@Getter
public class SongRequestDto {

    /*
    노래 제목
    아티스트
    장르
    설명
     */

    @NotBlank
    @Size(max = 30, message = "노래 제목은 30자 까지 입력 가능합니다.")
    private final String name;

    @NotBlank
    @Size(max = 25, message = "가수 이름은 25자까지 입력 가능합니다.")
    private final String singer;

    @NotBlank
    private final Genres genre;

    @Size(message = "설명은 최대 255자까지 입력 가능합니다.")
    private final String description;

    public SongRequestDto(String name, String singer, Genres genre, String description) {
        this.name = name;
        this.singer = singer;
        this.genre = genre;
        this.description = description;
    }
}
