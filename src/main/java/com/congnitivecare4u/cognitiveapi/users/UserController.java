package com.congnitivecare4u.cognitiveapi.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    User get(){
        User user = new User(null, "test user", "test_user@test.com");
        return user;
    }

    @PostMapping()
    ResponseEntity<?> create(@RequestBody @Valid User user) {

        User persistentUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(persistentUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
