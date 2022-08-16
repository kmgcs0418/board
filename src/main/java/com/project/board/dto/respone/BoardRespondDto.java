package com.project.board.dto.respone;

import com.project.board.domain.entity.Board;
import com.project.board.domain.entity.Comment;
import com.project.board.dto.request.CommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Builder
public class BoardRespondDto {

    private Long id;

    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int hits;
    private String username;

}
