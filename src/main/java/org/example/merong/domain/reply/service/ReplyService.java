package org.example.merong.domain.reply.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.merong.domain.comments.dto.request.CommentRequestDto;
import org.example.merong.domain.comments.dto.response.CommentResponseDto;
import org.example.merong.domain.comments.entity.Comment;
import org.example.merong.domain.comments.repository.CommentRepository;
import org.example.merong.domain.reply.dto.request.ReplyRequestDto;
import org.example.merong.domain.reply.dto.response.ReplyResponseDto;
import org.example.merong.domain.reply.entity.Reply;
import org.example.merong.domain.reply.repository.ReplyRepository;
import org.example.merong.domain.songs.SongRepository;
import org.example.merong.domain.user.entity.User;
import org.example.merong.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final ReplyRepository replyRepository;


    // 댓글 생성
    public ReplyResponseDto.Add saveReply(Long commentId, ReplyRequestDto.Add requestDto, Long userId) {

        // comment 객체 생성 및 초기화
        Reply reply = Reply.builder()
                .content(requestDto.getContent())
                .comment(commentRepository.findByIdOrElseThrow(commentId))
                .user(userRepository.findByIdOrElseThrow(userId))
                .build();

        // 댓글 생성
        replyRepository.save(reply);

        return new ReplyResponseDto.Add(
                reply.getUser().getId(),
                reply.getContent(),
                reply.getCreatedAt(),
                reply.getComment().getId()
        );
    }


    // 댓글 수정
    @Transactional
    public ReplyResponseDto.Update updateReply(Long commentId, ReplyRequestDto.Update requestDto, Long userId){

        //댓글 찾아오기 및 검증
        Reply findReply = validateReplyUser(commentId, userId);

        // 업데이트
        findReply.updateContent(requestDto.getContent());

        return new ReplyResponseDto.Update(
                findReply.getUser().getId(),
                findReply.getContent(),
                findReply.getUpdatedAt(),
                findReply.getComment().getId()
        );
    }


    // 댓글 삭제
    @Transactional
    public void deleteReply(Long replyId, Long userId){

        //댓글 찾아오기 및 검증
        Reply findReply = validateReplyUser(replyId, userId);

        // 댓글 삭제
        replyRepository.delete(findReply);
    }

    // 댓글 작성 유저와 로그인 유저 동일 검증 및 댓글 찾아오는 메소드
    private Reply validateReplyUser(Long replyId, Long userId){
        //대댓글 찾아오기
        Reply findReply = replyRepository.findByIdOrElseThrow(replyId);

        //유저 찾아오기
        User findUser = userRepository.findByIdOrElseThrow(userId);

        // 검증
        if(!findReply.getUser().getId().equals(findUser.getId())){
            throw new RuntimeException("정보가 일치하지 않습니다.");
        }

        return findReply;
    }

}
