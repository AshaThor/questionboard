package com.ashathor.questionboard.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({ "/", "/home" })
public class HomeController {

    //Generic home controller
    @GetMapping
    public String main(){
        return "home";
    }
}
