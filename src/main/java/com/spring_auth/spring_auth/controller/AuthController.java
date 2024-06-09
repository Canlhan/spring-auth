package com.spring_auth.spring_auth.controller;


import com.spring_auth.spring_auth.model.User;
import com.spring_auth.spring_auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController
{
    private final UserService userService;

    @PostMapping(path = "/register")
    public User add(@RequestBody User user){


        return userService.add(user);
    }

    @GetMapping(path = "/")
    public List<User> gets(){

        return userService.getUsers();
    }
}
