package com.godcoder.sion_21.controller;

import com.godcoder.sion_21.model.Board;
import com.godcoder.sion_21.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/list")
    public String list(Model model){
        //조회할때:
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards",boards);
        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required =false) Long id ){
        if(id==null){
            model.addAttribute("board",new Board()); //리퀘스트 안받았으면 바로 전달
        }else{
            Board board = boardRepository.findById(id).orElse(null); //없을경우에는 null
            model.addAttribute("board",board);
        }
        return "board/form";
    }
    @PostMapping("/form")
    public String greetingSubmit(@ModelAttribute Board board) {
        boardRepository.save(board); // 있을경우 업뎃,아니면 그거
        return "redirect:/board/list";//값을뿌려야함
    }


}
