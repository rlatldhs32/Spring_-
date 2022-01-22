package com.godcoder.sion_21.controller;

import com.godcoder.sion_21.model.Board;
import com.godcoder.sion_21.repository.BoardRepository;
import com.godcoder.sion_21.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model,@PageableDefault(size = 2) Pageable pageable,String searchText){
        Page<Board> boards = boardRepository.findAll(pageable);

        int startPage= Math.max(1,boards.getPageable().getPageNumber() -4);
        int endPage= Math.min(boards.getTotalPages(),boards.getPageable().getPageNumber() +4);

        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
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
    public String greetingSubmit(@Valid Board board, BindingResult bindingresult) { //사이즈같은 조건에
        // 맞지 않는다면
        boardValidator.validate(board,bindingresult);
        if (bindingresult.hasErrors()) {
            return "board/form";
        }
        boardRepository.save(board);
        return "redirect:/board/list";//값을뿌려야함
    }


}
