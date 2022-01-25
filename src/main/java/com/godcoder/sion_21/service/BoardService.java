package com.godcoder.sion_21.service;

import com.godcoder.sion_21.model.Board;
import com.godcoder.sion_21.model.User;
import com.godcoder.sion_21.repository.BoardRepository;
import com.godcoder.sion_21.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;


    public Board save(String username,Board board){
        User user =userRepository.findByUsername(username);
        board.setUser(user);
        return boardRepository.save(board);
    }
}
