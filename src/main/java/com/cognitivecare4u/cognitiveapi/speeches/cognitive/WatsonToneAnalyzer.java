package com.cognitivecare4u.cognitiveapi.speeches.cognitive;

import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.DocumentAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class WatsonToneAnalyzer implements CognitiveToneAnalyzer {

    @Value("${care4u.speeches.watson.tone.version}")
    private String version;

    @Value("${care4u.speeches.watson.tone.username}")
    private String username;

    @Value("${care4u.speeches.watson.tone.password}")
    private String password;

    private ToneAnalyzer toneAnalyzer;

    @PostConstruct
    public void configure() {
        this.toneAnalyzer = new ToneAnalyzer(version, username, password);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("X-Watson-Learning-Opt-Out", "true");
        this.toneAnalyzer.setDefaultHeaders(headers);
    }

    @Override
    public void analyze(String speech) {
        ToneOptions options =
                new ToneOptions.Builder()
                        .text(speech)
                        //.contentLanguage("pt-BR")
                        //.acceptLanguage("pt-br")
                        .build();
        ToneAnalysis analysis = this.toneAnalyzer.tone(options).execute();
        DocumentAnalysis documentAnalysis = analysis.getDocumentTone();
        log.info("Tone: {}", analysis);
        documentAnalysis.getToneCategories().forEach(toneCategory -> {
            log.info("Category: ", toneCategory.getCategoryName());
            toneCategory.getTones().forEach(toneScore -> {
                log.info("Category Tone Score: [{} - {}]", toneScore.getToneName(), toneScore.getScore());
            });
        });
        documentAnalysis.getTones().forEach(toneScore -> {
            log.info("Tone Score: [{} - {}]", toneScore.getToneName(), toneScore.getScore());
        });
        analysis.getSentencesTone().forEach(sentenceAnalysis -> {
            log.info("Sentences: {}", sentenceAnalysis.getText());
            sentenceAnalysis.getTones().forEach(toneScore -> {
                log.info("Sentence Tone Score: [{} - {}]", toneScore.getToneName(), toneScore.getScore());
            });
        });

    }

}
