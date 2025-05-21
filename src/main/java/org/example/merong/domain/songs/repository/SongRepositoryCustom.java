package org.example.merong.domain.songs.repository;

import org.example.merong.domain.searches.enums.Order;
import org.example.merong.domain.searches.enums.Type;
import org.example.merong.domain.songs.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SongRepositoryCustom {

    Page<Song> search(Type type, String keyword, Order order, Pageable pageable);

}
