package com.cognitivecare4u.cognitiveapi.speeches.cognitive;

import org.springframework.stereotype.Component;

@Component
public interface CognitiveLanguageTranslator {

    String translate(String text);

}
