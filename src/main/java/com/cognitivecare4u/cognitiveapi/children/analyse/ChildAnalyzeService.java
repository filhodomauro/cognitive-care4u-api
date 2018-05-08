package com.cognitivecare4u.cognitiveapi.children.analyse;

import com.cognitivecare4u.cognitiveapi.exceptions.AnalyzeException;
import com.cognitivecare4u.cognitiveapi.exceptions.UnprocessableEntityException;
import com.cognitivecare4u.cognitiveapi.speeches.SpeechService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Slf4j
@Service
public class ChildAnalyzeService {

    @Autowired
    private SpeechService speechService;

    public ChildAnalyze imageAnalyse(ChildAnalyzeRequest analyseRequest) {
        return null;
    }

    public ChildAnalyze speechAnalyse(ChildAnalyzeRequest analyseRequest) {
        try {
            InputStream speech = readFromResource(analyseRequest.getResourcePath());
            ChildAnalyze childAnalyze = speechService.analyse(speech, analyseRequest.getContentType());
            log.info("Analyze info: [happy:{}, sad:{}, bulling:{}, sexual:{}, profanity:{}]",
                    childAnalyze.getHappy(), childAnalyze.getSad(), childAnalyze.getBullying(),
                    childAnalyze.getSexualContent(), childAnalyze.getProfanity());
            return childAnalyze;
        } catch (IOException e) {
            log.error("Error to read speech resource", e);
            throw new AnalyzeException("Error to read speech resource", e);
        }
    }

    private InputStream readFromResource(String resource) throws IOException {
        URL url = new URL(resource);
        return url.openStream();
    }
}
