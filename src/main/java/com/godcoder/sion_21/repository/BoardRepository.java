package com.godcoder.sion_21.repository;

import com.godcoder.sion_21.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
}
