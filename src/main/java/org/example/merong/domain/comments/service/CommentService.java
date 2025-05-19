package org.example.merong.domain.comments.service;

import lombok.RequiredArgsConstructor;
import org.example.merong.domain.comments.dto.request.CommentRequestDto;
import org.example.merong.domain.comments.dto.response.CommentResponseDto;
import org.example.merong.domain.comments.dto.response.CommentResponseDto.Add;
import org.example.merong.domain.comments.entity.Comment;
import org.example.merong.domain.comments.repository.CommentRepository;
import org.example.merong.domain.songs.entity.Song;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

//    private final SongService songService;
//
//    private final UserService userService;
//
//    private CommentRepository commentRepository;
//
//    public CommentResponseDto.Add saveComment(Long songId, CommentRequestDto.Add requestDto, Long userId) {
//
//        Comment comment = Comment.builder()
//                .content(requestDto.getContent())
//                .song(songService.findById(songId))
//                .user(userService.findById(userId))
//                .build();
//
//        commentRepository.save(comment);
//
//        return new CommentResponseDto.Add(comment.getUser().getId(), )
//
//
//
//    }
}
