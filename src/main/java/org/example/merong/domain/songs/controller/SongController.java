package org.example.merong.domain.songs.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.merong.domain.auth.dto.UserAuth;
import org.example.merong.domain.songs.service.SongService;
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

    /**
     * 1. 노래 등록
     * @param auth
     * @param dto
     * @return 등록결과물 반환
     */
    @PostMapping
    public ResponseEntity<SongResponseDto.Create> createSong(@AuthenticationPrincipal UserAuth auth, @RequestBody @Valid SongRequestDto dto) {

        return new ResponseEntity<>(songService.createSong(auth.getId(), dto), HttpStatus.CREATED);

    }

    /**
     * 2. 내가 등록한 노래 조회
     * @param auth
     * @return 내가 등록한 노래 리스트 반환
     */
    @GetMapping
    public ResponseEntity<List<SongResponseDto.Get>> getMySongs(@AuthenticationPrincipal UserAuth auth) {

        return new ResponseEntity<>(songService.getSongs(auth.getId()), HttpStatus.OK);

    }

    /**
     * 3. 등록 노래 수정
     * @param auth
     * @param songId
     * @param dto
     * @return 수정 여부 반환
     */
    @PatchMapping("/{songId}")
    public ResponseEntity<SongResponseDto.Update> updateSong(@AuthenticationPrincipal UserAuth auth,
                                                      @PathVariable Long songId,
                                                      @RequestBody @Valid SongUpdateDto dto) {

        return new ResponseEntity<>(songService.updateSong(auth.getId(), songId, dto), HttpStatus.OK);

    }

    /**
     * 4. 등록 노래 삭제
     * @param auth
     * @param songId
     * @return NO_CONTENT
     */
    @DeleteMapping("/{songId}")
    public ResponseEntity<Void> deleteSong(@AuthenticationPrincipal UserAuth auth,
                                           @PathVariable Long songId) {

        songService.deleteSong(auth.getId(), songId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    /**
     * 5. 노래 검색
     */
    @GetMapping("/search")
    public ResponseEntity<List<SongResponseDto.Get>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(songService.search(keyword));
    }

    @GetMapping("/popular")
    public ResponseEntity<List<String>> popularKeywords() {
        return ResponseEntity.ok(songService.getPopularKeywords());
    }

}
