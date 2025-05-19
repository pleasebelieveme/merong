package org.example.merong.domain.comments.service;

import lombok.RequiredArgsConstructor;
import org.example.merong.domain.comments.dto.request.CommentRequestDto;
import org.example.merong.domain.comments.dto.response.CommentResponseDto;
import org.example.merong.domain.comments.entity.Comment;
import org.example.merong.domain.comments.repository.CommentRepository;
import org.example.merong.domain.songs.SongService;
import org.example.merong.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final SongService songService;

    private final UserService userService;

    private final CommentRepository commentRepository;

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
//        return new CommentResponseDto.Add(
//                comment.getUser().getId(),
//                comment.getContent(),
//                comment.getCreatedAt(),
//                comment.getSong().getId()
//        );
//    }

    @Transactional
    public CommentResponseDto.Update updateComment(Long commentId, CommentRequestDto.Update requestDto, Long userId){

        // 로그인 유저가 찾는 댓글 찾아오기
        Comment findComment = commentRepository.findByIdAndUserIdOrElseThrow(commentId, userId);

        // 업데이트
        findComment.updateContent(requestDto.getContent());

        return new CommentResponseDto.Update(
                findComment.getUser().getId(),
                findComment.getContent(),
                findComment.getUpdatedAt(),
                findComment.getSong().getId()
        );
    }


    @Transactional
    public void deleteComment(Long commentId, Long userId){
        Comment findComment = commentRepository.findByIdAndUserIdOrElseThrow(commentId, userId);

        commentRepository.delete(findComment);
    }
}
