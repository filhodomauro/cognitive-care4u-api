package com.cognitivecare4u.cognitiveapi.children.speeches;

import com.cognitivecare4u.cognitiveapi.speeches.cognitive.CognitiveSpeechToText;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Primary
@Component
public class MockSpeechToText implements CognitiveSpeechToText {
    @Override
    public String transform(InputStream speech, String contentType) {
        return "teste de transformação de fala em texto .";
    }
}
