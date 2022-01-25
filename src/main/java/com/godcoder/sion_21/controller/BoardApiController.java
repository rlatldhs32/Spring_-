package com.godcoder.sion_21.controller;


import com.godcoder.sion_21.model.Board;
import com.godcoder.sion_21.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BoardApiController {
    @Autowired
    private BoardRepository repository;

    @GetMapping("/boards")
    List<Board> all(@RequestParam(required = false,defaultValue = "") String title,
                    @RequestParam(required = false,defaultValue = "") String content) { //required= true : 파라미터가전달안되면 에러임.
        if(StringUtils.isEmpty(title)&& StringUtils.isEmpty(content)) { //전달 안됐으면 그냥 조회 다 함.
            return repository.findAll();
        }else{
            return repository.findByTitleOrContent(title,content);
        }
    }

    @PostMapping("/boards")
    Board newBoard(@RequestBody Board newBoard) {
        return repository.save(newBoard);
    }

    @GetMapping("/boards/{id}")
    Board one(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/boards/{id}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {

        return repository.findById(id)
                .map(Board -> {
                    Board.setTitle(newBoard.getTitle());
                    Board.setContent(newBoard.getContent());
                    return repository.save(Board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }

    @Secured("ROLE_ADMIN") //권한을 줌. 원래는 그냥 호추ㄹ주면없앨수도있었음.
    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Long id) {
        repository.deleteById(id);
    }
}