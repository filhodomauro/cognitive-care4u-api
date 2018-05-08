package com.cognitivecare4u.cognitiveapi.children.speeches;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/children/{childId}/speeches")
public class ChildSpeechController {

    @Autowired
    private ChildSpeechService childSpeechService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody @Valid ChildSpeech speech, @PathVariable String childId, BindingResult bindingResult) {
        speech.setChildId(childId);
        ChildSpeech childSpeech = childSpeechService.save(speech);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(childSpeech.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ChildSpeech>> list(@PathVariable String childId) {
        List<ChildSpeech> speeches = childSpeechService.list(childId);
        return ResponseEntity.ok(speeches);
    }
}