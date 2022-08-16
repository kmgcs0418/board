package com.project.board.service;

import com.project.board.domain.entity.Board;
import com.project.board.domain.entity.Comment;
import com.project.board.domain.entity.Member;
import com.project.board.domain.repository.CommentRepository;
import com.project.board.dto.request.CommentRequestDto;
import com.project.board.dto.respone.BoardRespondDto;
import com.project.board.dto.respone.CommentRespondDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Long saveComment(CommentRequestDto commentRequestDto, Member member, Board board) {

        Comment comment = commentRequestDto.toEntity(member, board);
        commentRepository.save(comment);
        return comment.getId();
    }


    @Transactional
    public CommentRespondDto getComment(Long id) {
        Comment comment = commentRepository.findById(id).get();

        CommentRespondDto commentRespondDto = CommentRespondDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdDate(comment.getCreatedDate())
                .modifiedDate(comment.getModifiedDate())
                .username(comment.getMember().getUsername())
                .boardId(comment.getBoard().getId())
                .build();

        return commentRespondDto;
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

}
