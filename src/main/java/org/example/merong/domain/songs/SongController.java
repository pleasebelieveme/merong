package org.example.merong.domain.songs;

import lombok.RequiredArgsConstructor;
import org.example.merong.domain.songs.dto.request.SongRequestDto;
import org.example.merong.domain.songs.dto.response.SongResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/songs")
public class SongController {

    private final SongService songService;

    // 1. 노래 등록
    @PostMapping
    public ResponseEntity<SongResponseDto> createSong(@RequestBody SongRequestDto dto) {

        return null;
    }
    // 2. 내 노래 조회
    @GetMapping
    public ResponseEntity<List<SongResponseDto>> getMySongs() {

        return null;
    }
    // 3. 노래 수정
    @PatchMapping("/{songid}")
    public ResponseEntity<SongResponseDto> updateSong(@PathVariable Long songId) {

        return null;
    }
    // 4. 노래 삭제
    @PatchMapping("/{songid}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long songid) {

        return ResponseEntity.noContent().build();
    }

}
