package com.project.board.dto.request;

import com.project.board.domain.entity.Board;
import com.project.board.domain.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class BoardFormDto {

    private Long id;
    private String title;

    private String content;

    // boardFromDto -> Board Entity
    public Board toEntity(Member member){
        return Board.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .member(member)
                .build();
    }
}
