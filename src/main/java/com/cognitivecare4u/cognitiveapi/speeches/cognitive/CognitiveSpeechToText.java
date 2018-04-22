package com.cognitivecare4u.cognitiveapi.speeches.cognitive;

import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public interface CognitiveSpeechToText {

    String transform(InputStream speech, String contentType);
}
