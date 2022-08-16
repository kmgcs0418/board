package com.project.board.domain.repository;

import com.project.board.domain.entity.Board;
import com.project.board.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Modifying
    @Query("update Board b set b.hits = b.hits + 1 where b.id = ?1")
    int updateHits(Long id);

    Page<Board> findByTitleContaining(String keyword, Pageable pageable);

    Optional<Board> findById(Long id);
}