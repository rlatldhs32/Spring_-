package com.godcoder.sion_21.repository;

import com.godcoder.sion_21.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {

    List<Board> findByTitle(String title);
    List<Board> findByTitleOrContent(String title,String content);

    Page<Board>.find
}
