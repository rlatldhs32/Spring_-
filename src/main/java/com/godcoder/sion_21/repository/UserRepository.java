package com.godcoder.sion_21.repository;

import com.godcoder.sion_21.model.Board;
import com.godcoder.sion_21.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
