package com.godcoder.sion_21.controller;


import com.godcoder.sion_21.model.User;
import com.godcoder.sion_21.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(){
        return "account/login";
    }


    @GetMapping("/register")
    public String register(){ //사용자 저장해야함.
        return "account/register";
    }

    @PostMapping("/register")
    public String register(User user){ //사용자 저장해야함.
        userService.save(user);
        //로그인이 완료되면 홈으로가면되니까까
       return "redirect:/";
    }


}
