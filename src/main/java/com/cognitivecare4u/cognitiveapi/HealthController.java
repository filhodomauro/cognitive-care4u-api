package com.cognitivecare4u.cognitiveapi;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthController {

    @GetMapping(value = "/ping", produces = "plain/text")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }
}
