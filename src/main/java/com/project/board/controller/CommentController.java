package com.project.board.controller;

import com.project.board.domain.entity.Board;
import com.project.board.domain.entity.Comment;
import com.project.board.domain.entity.Member;
import com.project.board.domain.repository.BoardRepository;
import com.project.board.domain.repository.CommentRepository;
import com.project.board.domain.repository.MemberRepository;
import com.project.board.dto.request.BoardFormDto;
import com.project.board.dto.request.CommentRequestDto;
import com.project.board.dto.respone.BoardRespondDto;
import com.project.board.service.BoardService;
import com.project.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @PostMapping("/post/{id}/comment")
    public String writeComment (@PathVariable Long id, @ModelAttribute CommentRequestDto commentRequestDto, @AuthenticationPrincipal User user) {

        Member member = memberRepository.findByUsername(user.getUsername()).get();

        Board board = boardRepository.findById(id).get();

        commentService.saveComment(commentRequestDto, member, board);

        return "redirect:/post/{id}";
    }

    /*@DeleteMapping("/post/comment/{id}")
    public String delete(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
        return "board/view.html";
    }*/

}
