package org.example.merong.common.advice;

import lombok.RequiredArgsConstructor;
import org.example.merong.domain.songs.dto.request.SongRequestDto;
import org.example.merong.domain.songs.entity.Song;
import org.example.merong.domain.songs.enums.Genres;
import org.example.merong.domain.songs.repository.SongRepository;
import org.example.merong.domain.user.entity.User;
import org.example.merong.domain.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

// 더미데이터 생성
@Component
@RequiredArgsConstructor
public class DummyDataInitializer implements CommandLineRunner {

    private final SongRepository songRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (!Arrays.asList(args).contains("--initDummyData")) return ;

        User user = userRepository.findById(1L).orElseThrow();

        for (int i = 0; i < 50000; i++) {
            Song song = new Song(
                    user,
                    new SongRequestDto("노래" + i, "가수" + i, Genres.BALLAD, "설명" + i)
            );
            songRepository.save(song);
        }
    }
}
