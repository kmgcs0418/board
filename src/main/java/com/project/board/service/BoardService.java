package com.project.board.service;

import com.project.board.domain.entity.Board;
import com.project.board.domain.entity.Comment;
import com.project.board.domain.entity.Member;
import com.project.board.domain.repository.BoardRepository;
import com.project.board.dto.request.BoardFormDto;
import com.project.board.dto.respone.BoardRespondDto;
import com.project.board.dto.respone.CommentRespondDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class BoardService {
    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public Long savePost(BoardFormDto boardFormDto, Member member) {

        // boardFromDto -> Entity 작업 필요
        Board board = boardFormDto.toEntity(member);

        boardRepository.save(board);

        return board.getId();
    }

    @Transactional
    public Page<Board> pageList(Pageable pageable) {

        return boardRepository.findAll(pageable);
    }

    /*@Transactional
    public List<BoardDto> getBoardList() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for(Board board : boardList) {
            BoardDto boardDto = BoardDto.builder()
                    .id(board.getId())
                    .author(board.getAuthor())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .hits(board.getHits())
                    .createdDate(board.getCreatedDate())
                    .build();
            boardDtoList.add(boardDto);
        }
        return boardDtoList;
    }*/

    @Transactional
    public BoardRespondDto getPost(Long id) {
        Board board = boardRepository.findById(id).get();

        BoardRespondDto responseDto = BoardRespondDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .createdDate(board.getCreatedDate())
                .modifiedDate(board.getModifiedDate())
                .hits(board.getHits())
                .username(board.getMember().getUsername())
                .build();


//        BoardDto boardDto = BoardDto.builder()
//                //.id(board.getId())
//                .title(board.getTitle())
//                .content(board.getContent())
//                .hits(board.getHits())
//                .createdDate(board.getCreatedDate())
//                //.member(board.getMember())
//                .build();
        return responseDto;
    }

    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

    //조회수
    @Transactional
    public int updateHits(Long id) {
        return boardRepository.updateHits(id);
    }

    //검색
    public Page<Board> search(String keyword, Pageable pageable) {
        Page<Board> boardList = boardRepository.findByTitleContaining(keyword, pageable);
        return boardList;
    }

}