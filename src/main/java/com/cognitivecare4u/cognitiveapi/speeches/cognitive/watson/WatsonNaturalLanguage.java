package com.cognitivecare4u.cognitiveapi.speeches.cognitive.watson;

import com.cognitivecare4u.cognitiveapi.analyzes.Analyze;
import com.cognitivecare4u.cognitiveapi.speeches.cognitive.CognitiveNaturalLanguage;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class WatsonNaturalLanguage implements CognitiveNaturalLanguage {

    @Value("${care4u.natural_language_understand.watson.version}")
    private String version;

    @Value("${care4u.natural_language_understand.watson.username}")
    private String username;

    @Value("${care4u.natural_language_understand.watson.password}")
    private String password;

    @Value("#{'${care4u.natural_language_understand.watson.sexual_categories}'.split(',')}")
    private List<String> sexualCategories;

    @Value("#{'${care4u.natural_language_understand.watson.profanity_categories}'.split(',')}")
    private List<String> profanityCategories;

    private NaturalLanguageUnderstanding naturalLanguage;

    @PostConstruct
    public void configure() {
        this.naturalLanguage = new NaturalLanguageUnderstanding(version, username, password);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("X-Watson-Learning-Opt-Out", "true");
        this.naturalLanguage.setDefaultHeaders(headers);
    }

    @Override
    public Analyze analyze(String speech) {
        EmotionOptions emotionOptions =
                new EmotionOptions.Builder()
                .build();

        CategoriesOptions categoriesOptions =
                new CategoriesOptions();

        Features features = new Features.Builder()
                .emotion(emotionOptions)
                .categories(categoriesOptions)
                .build();

        AnalyzeOptions parameters = new AnalyzeOptions.Builder()
                .text(speech)
                .features(features)
                .build();
        AnalysisResults analysisResults = this.naturalLanguage
                .analyze(parameters)
                .execute();
        log.info("NL: {}", analysisResults);

        Analyze childAnalyze = new Analyze();

        Optional.ofNullable(analysisResults.getEmotion())
                .ifPresent(emotionResult -> {
                    EmotionScores scores = emotionResult.getDocument().getEmotion();
                    childAnalyze.setHappy(scores.getJoy().floatValue());
                    childAnalyze.setSad(scores.getSadness().floatValue());
                    childAnalyze.setBullying(scores.getFear().floatValue());
                });
        Optional.ofNullable(analysisResults.getCategories())
                .ifPresent(categoriesResults -> {
                    categoriesResults.forEach(categoriesResult -> {
                        if (this.sexualCategories.contains(categoriesResult.getLabel())) {
                            childAnalyze.setSexualContent(1f);
                        }
                        if (this.profanityCategories.contains(categoriesResult.getLabel())) {
                            childAnalyze.setProfanity(1f);
                        }
                    });
                });
        return childAnalyze;
    }
}
