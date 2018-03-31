package com.cognitivecare4u.cognitiveapi.children;

import com.cognitivecare4u.cognitiveapi.FieldError;
import com.cognitivecare4u.cognitiveapi.exceptions.NotFoundException;
import com.cognitivecare4u.cognitiveapi.exceptions.UnprocessableEntityException;
import com.cognitivecare4u.cognitiveapi.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/children")
public class ChildController {

    @Autowired
    public ChildRepository childRepository;

    @Autowired
    public UserRepository userRepository;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Child get(@PathVariable String id){
        Optional<Child> child = childRepository.findById(id);
        if (child.isPresent()) {
            return child.get();
        } else {
            throw new NotFoundException();
        }
    }

    @PostMapping
    ResponseEntity<?> create(@RequestBody @Valid Child child, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new UnprocessableEntityException("Error to save child", bindingResult.getFieldErrors());
        }

        child.getParentUsersIds().forEach(parentUserId -> {
            if (!isValidParentUser(parentUserId)) {
                throw new UnprocessableEntityException("Error to save child", FieldError.of("parentUserId", "invalid parent user id"));
            }
        });
        Child persistentChild = childRepository.save(child);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(persistentChild.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    private boolean isValidParentUser(String parentUserId) {
        return userRepository.findById(parentUserId).isPresent();
    }

}
