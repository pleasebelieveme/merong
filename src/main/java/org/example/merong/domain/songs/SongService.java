package org.example.merong.domain.songs;

import lombok.RequiredArgsConstructor;
import org.example.merong.domain.songs.dto.request.SongRequestDto;
import org.example.merong.domain.songs.dto.request.SongUpdateDto;
import org.example.merong.domain.songs.dto.response.SongResponseDto;
import org.example.merong.domain.songs.entity.Song;
import org.example.merong.domain.songs.exception.SongException;
import org.example.merong.domain.songs.exception.SongsExceptionCode;
import org.example.merong.domain.user.entity.User;
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
    public SongResponseDto.Create createSong(Long userId, SongRequestDto dto) {

        User currentUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException());

        Song song = songRepository.save(new Song(dto));

        return new SongResponseDto.Create(song);
    }

    // 2. 내 노래 전체 조회
    @Transactional(readOnly = true)
    public List<SongResponseDto.Get> getSongs(Long userId) {

        User currentUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException());

        return currentUser.getSongs().stream().map(SongResponseDto.Get::new).collect(Collectors.toList());

    }

    // 3. 노래 수정
    public SongResponseDto.Update updateSong(Long userId, Long songId, SongUpdateDto dto) {

        User currentUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException());
        Song song = songRepository.findByIdOrElseThrow(songId);

        // 검증
        if (currentUser.getId().equals(song.getUser().getId())) {
            throw new SongException(SongsExceptionCode.SONG_OWNERSHIP_EXCEPTION);
        }

        song.updateSong(dto);
        songRepository.save(song);

        return new SongResponseDto.Update(song);

    }

    // 4. 노래 삭제
    public void deleteSong(Long userId, Long songId) {

        User currentUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException());
        Song song = songRepository.findByIdOrElseThrow(songId);

        // 검증
        if (currentUser.getId().equals(song.getUser().getId())) {
            throw new SongException(SongsExceptionCode.SONG_OWNERSHIP_EXCEPTION);
        }

        songRepository.delete(song);

    }
}
