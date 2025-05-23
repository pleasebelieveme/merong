package org.example.merong.domain.songs.dto.response;

import lombok.Getter;

@Getter
public class ViewPlayCntDto {

    private final Long playCount;

    public ViewPlayCntDto(Long playCount) {
        this.playCount = playCount;
    }
}
