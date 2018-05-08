package com.cognitivecare4u.cognitiveapi.children.analyze;

import com.cognitivecare4u.cognitiveapi.CognitiveApiApplication;
import com.cognitivecare4u.cognitiveapi.children.analyse.ChildAnalyzeRequest;
import com.cognitivecare4u.cognitiveapi.children.analyse.ChildAnalyzeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CognitiveApiApplication.class)
public class ChildAnalyzeServiceTest {

    @Autowired
    private ChildAnalyzeService childAnalyzeService;

    @Test
    public void analyzeTextFromRemote() {
        ChildAnalyzeRequest analyzeRequest = new ChildAnalyzeRequest();
        analyzeRequest.setContentType("audio/mpeg");
        analyzeRequest.setResourcePath("https://firebasestorage.googleapis.com/v0/b/cc4y-fiap.appspot.com/o/teste.mp3?alt=media&token=7d130718-ea7f-4874-918f-e0f8ca5b93b0");
        childAnalyzeService.speechAnalyse(analyzeRequest);
    }
}
