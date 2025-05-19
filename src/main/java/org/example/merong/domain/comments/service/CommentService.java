package org.example.merong.domain.comments.service;

import lombok.RequiredArgsConstructor;
import org.example.merong.domain.comments.dto.request.CommentRequestDto;
import org.example.merong.domain.comments.dto.response.CommentResponseDto;
import org.example.merong.domain.comments.entity.Comment;
import org.example.merong.domain.comments.repository.CommentRepository;
import org.example.merong.domain.songs.SongRepository;
import org.example.merong.domain.songs.SongService;
import org.example.merong.domain.songs.entity.Song;
import org.example.merong.domain.user.entity.User;
import org.example.merong.domain.user.repository.UserRepository;
import org.example.merong.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {


    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final SongRepository songRepository;


    // 댓글 생성
    public CommentResponseDto.Add saveComment(Long songId, CommentRequestDto.Add requestDto, Long userId) {

        // comment 객체 생성 및 초기화
        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .song(songRepository.findByIdOrElseThrow(songId))
                .user(userRepository.findByIdOrElseThrow(userId))
                .build();

        // 댓글 생성
        commentRepository.save(comment);

        return new CommentResponseDto.Add(
                comment.getUser().getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getSong().getId()
        );
//        return null;
    }


    // 댓글 수정
    @Transactional
    public CommentResponseDto.Update updateComment(Long commentId, CommentRequestDto.Update requestDto, Long userId){

        //댓글 찾아오기 및 검증
        Comment findComment = validateCommentUser(commentId, userId);

        // 업데이트
        findComment.updateContent(requestDto.getContent());

        return new CommentResponseDto.Update(
                findComment.getUser().getId(),
                findComment.getContent(),
                findComment.getUpdatedAt(),
                findComment.getSong().getId()
        );
    }


    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, Long userId){

        //댓글 찾아오기 및 검증
        Comment findComment = validateCommentUser(commentId, userId);

        // 댓글 삭제
        commentRepository.delete(findComment);
    }

    // 댓글 작성 유저와 로그인 유저 동일 검증 및 댓글 찾아오는 메소드
    private Comment validateCommentUser(Long commentId, Long userId){
        //댓글 찾아오기
        Comment findComment = commentRepository.findByIdOrElseThrow(commentId);

        //유저 찾아오기
        User findUser = userRepository.findByIdOrElseThrow(userId);

        // 검증
        if(!findComment.getUser().getId().equals(findUser.getId())){
            throw new RuntimeException("정보가 일치하지 않습니다.");
        }

        return findComment;
    }
}
