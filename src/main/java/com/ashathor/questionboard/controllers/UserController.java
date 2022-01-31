package com.ashathor.questionboard.controllers;

import com.ashathor.questionboard.models.User;
import com.ashathor.questionboard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


public class UserController {

    //TODO

    @Autowired
    private UserRepository userRepository;

    public User get(@PathVariable("user") String name ){
        return userRepository.findByUsername(name);
    }

}
