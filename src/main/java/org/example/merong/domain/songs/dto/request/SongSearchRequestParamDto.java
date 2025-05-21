package org.example.merong.domain.songs.dto.request;


import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.merong.domain.songs.enums.Genres;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SongSearchRequestParamDto {

    private String title;

    private String singer;

    private Genres genre;

    private Sort.Direction direction;

    private String sort;

    private Integer page;

    private Integer size;

    public void setPage(Integer page) {
        this.page = (page == null) ? 0 : page;
    }

    public void setSize(Integer size) {
        this.size = (size == null) ? 10 : size;
    }

    public void setSort(String sort) {
        this.sort = (sort == null) ? "createdAt" : sort;
    }

    public void setDirection(Sort.Direction direction) {
        this.direction = (direction == null) ? Sort.Direction.ASC : direction;
    }

}
