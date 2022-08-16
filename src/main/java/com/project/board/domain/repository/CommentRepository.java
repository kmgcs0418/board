package com.project.board.domain.repository;

import com.project.board.domain.entity.Board;
import com.project.board.domain.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    //Optional<Comment> findById(Long id);

    @Query("Select c from Comment c where c.board.id = :id")
    List<Comment> findCommentBoardId(@Param("id") Long id);

}
