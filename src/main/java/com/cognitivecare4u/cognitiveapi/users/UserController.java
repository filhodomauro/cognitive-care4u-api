package com.cognitivecare4u.cognitiveapi.users;

import com.cognitivecare4u.cognitiveapi.exceptions.UnprocessableEntityException;
import com.cognitivecare4u.cognitiveapi.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    User get(@PathVariable String id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NotFoundException();
        }
    }

    @PostMapping
    ResponseEntity<?> create(@RequestBody @Valid User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new UnprocessableEntityException("Error to save user", bindingResult.getFieldErrors());
        }
        User persistentUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(persistentUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}