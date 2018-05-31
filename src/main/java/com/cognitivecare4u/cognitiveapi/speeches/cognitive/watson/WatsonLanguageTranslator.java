package com.cognitivecare4u.cognitiveapi.speeches.cognitive.watson;

import com.cognitivecare4u.cognitiveapi.speeches.cognitive.CognitiveLanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslateOptions;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslationResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class WatsonLanguageTranslator implements CognitiveLanguageTranslator {

    @Value("${care4u.language_translate.watson.username}")
    private String username;

    @Value("${care4u.language_translate.watson.password}")
    private String password;

    private LanguageTranslator languageTranslator;

    @PostConstruct
    public void configure() {
        this.languageTranslator = new LanguageTranslator(this.username, this.password);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("X-Watson-Learning-Opt-Out", "true");
        this.languageTranslator.setDefaultHeaders(headers);
    }

    @Override
    public String translate(String text) {
        TranslateOptions translateOptions =
                new TranslateOptions.Builder()
                        .source("pt-BR")
                        .target("en")
                        .addText(text)
                        .build();
        TranslationResult translationResult =
                this.languageTranslator.translate(translateOptions).execute();

        StringBuilder translated = new StringBuilder();

        Optional
                .ofNullable(translationResult.getTranslations())
                .ifPresent(translations -> {
                    translations.forEach(translation -> {
                        translated.append(translation.getTranslation()).append(".");
                    });
                });
        return translated.toString();
    }
}
