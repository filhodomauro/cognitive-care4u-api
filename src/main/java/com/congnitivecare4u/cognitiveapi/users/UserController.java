package com.congnitivecare4u.cognitiveapi.users;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping()
    User get(){
        User user = new User();
        user.setName("teste user");
        return user;
    }

    @PostMapping()
    ResponseEntity<?> create(@RequestBody User user) {

        user.setId("id teste user");

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
