package org.example.merong.domain.songs;

import lombok.RequiredArgsConstructor;
import org.example.merong.domain.songs.dto.request.SongRequestDto;
import org.example.merong.domain.songs.dto.request.SongUpdateDto;
import org.example.merong.domain.songs.dto.response.SongResponseDto;
import org.example.merong.domain.songs.entity.Song;
import org.example.merong.domain.user.entity.User;
import org.example.merong.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SongService {

    private final SongRepository songRepository;
    private final UserRepository userRepository;

    // 1. 노래 등록
    public SongResponseDto createSong(Long userId, SongRequestDto dto) {

        User currentUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException());

        Song song = songRepository.save(new Song(dto));

        return new SongResponseDto(song);
    }

    // 2. 내 노래 전체 조회
    @Transactional(readOnly = true)
    public List<SongResponseDto> getSongs(Long userId) {

        User currentUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException());

        return currentUser.getSongs().stream().map(SongResponseDto::new).collect(Collectors.toList());

    }
    // 3. 노래 수정
    public SongResponseDto updateSong(Long userId, Long songId, SongUpdateDto dto) {

        User currentUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException());
        Song song = songRepository.findById(songId).orElseThrow(() -> new RuntimeException());

        // 검증
        if(currentUser.getId().equals(song.getUser().getId())) {
           throw new RuntimeException("정보가 일치하지 않습니다.");
        }

        song.updateSong(dto);
        songRepository.save(song);

        return new SongResponseDto(song);

    }

    // 4. 노래 삭제
    public void deleteSong(Long userId, Long songId) {

        User currentUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException());
        Song song = songRepository.findById(songId).orElseThrow(() -> new RuntimeException());

        // 검증
        if(currentUser.getId().equals(song.getUser().getId())) {
            throw new RuntimeException("정보가 일치하지 않습니다.");
        }

        songRepository.delete(song);

    }
}
