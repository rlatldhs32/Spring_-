package com.godcoder.sion_21.controller;

import com.godcoder.sion_21.model.Board;
import com.godcoder.sion_21.repository.BoardRepository;
import com.godcoder.sion_21.service.BoardService;
import com.godcoder.sion_21.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private BoardService boardService;

    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model,@PageableDefault(size = 2) Pageable pageable,
                       @RequestParam(required = false,defaultValue = "") String searchText){
       // Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText,searchText,pageable);

        int startPage= Math.max(1,boards.getPageable().getPageNumber() -4);

        int endPage= Math.min(boards.getTotalPages(),boards.getPageable().getPageNumber() +4);
        endPage=Math.max(endPage,startPage);

        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boards",boards);
        return "bo2ard/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required =false) Long id ){
        if(id==null){
            model.addAttribute("board",new Board()); //???????????? ??????????????? ?????? ??????
        }else{
            Board board = boardRepository.findById(id).orElse(null); //?????????????????? null
            model.addAttribute("board",board);
        }
        return "board/form";
    }
    @PostMapping("/form")
    //??????????????? ????????? ???????????????
    public String postForm(@Valid Board board, BindingResult bindingresult, Authentication authentication) { //??????????????? ?????????
        // ?????? ????????????
        boardValidator.validate(board,bindingresult);
        if (bindingresult.hasErrors()) {
            return "board/form";
        }
        String username = authentication.getName();
        boardService.save(username,board);
        return "redirect:/board/list";//??????????????????
    }


}
