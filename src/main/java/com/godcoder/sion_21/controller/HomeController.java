package com.godcoder.sion_21.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping
    public String index(){
        return "good";
    }

//    @GetMapping("/sion/sion")
//    public String sion(){return "good";}
}
