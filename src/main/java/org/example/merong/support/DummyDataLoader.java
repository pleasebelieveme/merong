package org.example.merong.support;


import lombok.RequiredArgsConstructor;
import org.example.merong.domain.songs.entity.Song;
import org.example.merong.domain.songs.repository.SongRepository;
import org.example.merong.domain.user.entity.User;
import org.example.merong.domain.user.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DummyDataLoader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final SongRepository songRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 유저 조회 (있으면 사용, 없으면 생성)
        User user = userRepository.findByEmail("dummy@example.com")
                .orElseGet(() -> {
                    System.out.println("더미 유저가 없어 생성함");
                    return userRepository.save(new User("dummy@example.com", "Dummy User", "dummy1234"));
                });

        // 이 유저가 등록한 곡이 없다면 생성
        if (songRepository.count() == 0) {
            for (int i = 1; i <= 100; i++) {
                Song song = new Song(user, "Dummy Title " + i, "Dummy Singer " + (i % 10));
                songRepository.save(song);
            }
            System.out.println("더미 노래 100개 생성 완료");
        } else {
            System.out.println("이미 노래가 존재함. 더미 생성 생략");
        }
    }

}
