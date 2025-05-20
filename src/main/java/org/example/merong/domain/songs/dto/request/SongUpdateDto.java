package org.example.merong.domain.songs.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.merong.domain.songs.enums.Genres;

@Getter
@AllArgsConstructor
public class SongUpdateDto {

    /*
    노래 제목
    아티스트
    장르
    설명
     */

    @Size(max = 30, message = "노래 제목은 30자 까지 입력 가능합니다.")
    private String name;

    @Size(max = 25, message = "가수 이름은 25자까지 입력 가능합니다.")
    private String singer;

    private Genres genre;

    @Size(message = "설명은 최대 255자까지 입력 가능합니다.")
    private String description;


}
