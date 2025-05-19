package org.example.merong.domain.songs;

import lombok.RequiredArgsConstructor;
import org.example.merong.common.security.CustomUserDetails;
import org.example.merong.domain.songs.dto.request.SongRequestDto;
import org.example.merong.domain.songs.dto.request.SongUpdateDto;
import org.example.merong.domain.songs.dto.response.SongResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/songs")
public class SongController {

    private final SongService songService;

    // 1. 노래 등록
    @PostMapping
    public ResponseEntity<SongResponseDto> createSong(@AuthenticationPrincipal CustomUserDetails userDetail, @RequestBody SongRequestDto dto) {

        return ResponseEntity.status(songService.createSong(userDetail.getUser().getId(), dto), HttpStatus.CREATED);
    }
    // 2. 내 노래 조회
    @GetMapping
    public ResponseEntity<List<SongResponseDto>> getMySongs(@AuthenticationPrincipal CustomUserDetails userDetail) {

        return ResponseEntity.status(songService.getSongs(userDetail.getUser().getId()), HttpStatus.OK);
    }
    // 3. 노래 수정
    @PatchMapping("/{songid}")
    public ResponseEntity<SongResponseDto> updateSong(@AuthenticationPrincipal CustomUserDetails userDetail,
                                                      @PathVariable Long songId,
                                                      @RequestBody SongUpdateDto dto) {

        return ResponseEntity.status(songService.updateSong(userDetail.getUser().getId(), songId, dto), HttpStatus.OK);
    }
    // 4. 노래 삭제
    @PatchMapping("/{songid}")
    public ResponseEntity<Void> deleteSong(@AuthenticationPrincipal CustomUserDetails userDetail,
                                           @PathVariable Long songid) {

        return ResponseEntity.status(songService.deleteSong(userDetail.getUser().getId(), songid), HttpStatus.NO_CONTENT);
    }

}
