package com.project.board.dto.request;

import com.project.board.domain.entity.Board;
import com.project.board.domain.entity.Comment;
import com.project.board.domain.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class CommentRequestDto {

    private Long id;
    private String content;


    public Comment toEntity(Member member, Board board){
        return Comment.builder()
                .id(this.id)
                .content(this.content)
                .member(member)
                .board(board)
                .build();
    }
}
