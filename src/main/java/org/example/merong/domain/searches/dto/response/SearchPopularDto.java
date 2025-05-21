package org.example.merong.domain.searches.dto.response;

import lombok.Getter;

@Getter
public class SearchPopularDto {

    private final int rank;
    private final String title;

    public SearchPopularDto(int rank, String title) {
        this.rank = rank;
        this.title = title;
    }
}
