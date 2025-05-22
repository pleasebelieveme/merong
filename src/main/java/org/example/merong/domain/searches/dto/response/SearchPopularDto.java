package org.example.merong.domain.searches.dto.response;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class SearchPopularDto {

//    @Serial
//    private static final long serialVersionUID = 2L;

    private int rank;
    private String title;

    public SearchPopularDto(int rank, String title) {
        this.rank = rank;
        this.title = title;
    }
}
