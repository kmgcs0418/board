package com.project.board.controller;

import com.project.board.domain.entity.Board;
import com.project.board.domain.entity.Comment;
import com.project.board.domain.entity.Member;
import com.project.board.domain.repository.CommentRepository;
import com.project.board.domain.repository.MemberRepository;
import com.project.board.dto.request.BoardFormDto;
import com.project.board.dto.respone.BoardRespondDto;
import com.project.board.dto.respone.CommentRespondDto;
import com.project.board.service.BoardService;
import com.project.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService boardService;

    private final CommentService commentService;

    private final CommentRepository commentRepository;

    private final MemberRepository memberRepository;


    @GetMapping("/")
    public String list(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        model.addAttribute("postList", boardService.pageList(pageable));
        model.addAttribute("page", boardService.pageList(pageable).getPageable().getPageNumber());
        model.addAttribute("hasNext", boardService.pageList(pageable).hasNext());
        model.addAttribute("hasPrev", boardService.pageList(pageable).hasPrevious());

        return "board/list.html";
    }

    /*@GetMapping("/")
    public String list(Model model) {
        List<BoardDto> boardDtoList = boardService.getBoardList();
        model.addAttribute("postList", boardDtoList);
        return "board/list.html";
    }*/

    @GetMapping("/boardForm")
    @Secured({"ROLE_MEMBER"})
    public String post() {
        return "board/post.html";
    }

    @PostMapping("/boardForm")
    @Secured({"ROLE_MEMBER"})
    public String write(@ModelAttribute BoardFormDto boardFormDto, @AuthenticationPrincipal User user) {

        // 게시글을 작성한 사람을 DB로부터 조회
        Member member = memberRepository.findByUsername(user.getUsername()).get();

        boardService.savePost(boardFormDto, member);

        return "redirect:/";
    }

    @GetMapping("/post/{id}")
    public String view(@PathVariable("id") Long id, Model model) {

        boardService.updateHits(id);

        BoardRespondDto respondDto = boardService.getPost(id);

        List<Comment> comments = commentRepository.findCommentBoardId(id);

        model.addAttribute("post", respondDto);
        model.addAttribute("commentList", comments);
        return "board/view.html";
    }

    @GetMapping("/post/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {

        BoardRespondDto respondDto = boardService.getPost(id);

        model.addAttribute("post", respondDto);

        return "board/edit.html";
    }

    @PutMapping("/post/edit/{id}")
    public String update(BoardFormDto boardFormDto, @AuthenticationPrincipal User user) {

        Member member = memberRepository.findByUsername(user.getUsername()).get();

        boardService.savePost(boardFormDto, member);

        return "redirect:/";
    }

    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.deletePost(id);
        return "board/list.html";
    }

    //검색, 페이징
    @GetMapping("/post/search")
    public String search(String keyword, Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Board> searchList = boardService.search(keyword, pageable);

        model.addAttribute("searchList", searchList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", searchList.getPageable().getPageNumber());
        model.addAttribute("hasNext",searchList.hasNext());
        model.addAttribute("hasPrev", searchList.hasPrevious());

        return "board/search.html";
    }
}