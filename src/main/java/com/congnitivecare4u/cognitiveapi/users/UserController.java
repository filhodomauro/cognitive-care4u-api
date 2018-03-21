package com.congnitivecare4u.cognitiveapi.users;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/users")
    public User get(){
        User user = new User();
        user.setName("teste user");
        return user;
    }
}
