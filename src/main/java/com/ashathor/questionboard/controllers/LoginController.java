package com.ashathor.questionboard.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    //Generic login controller
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping({"/home", "/"})
    public String index() {
        return "home";
    }

}
