package com.spring_auth.spring_auth.service;

import com.spring_auth.spring_auth.model.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    public List<User> getUsers();
    User add(User user);

}
