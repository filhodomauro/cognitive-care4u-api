package com.cognitivecare4u.cognitiveapi.children.speeches;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ChildSpeechService {

    @Autowired
    private ChildSpeechRepository childSpeechRepository;

    public ChildSpeech save(ChildSpeech childSpeech) {
        return childSpeechRepository.save(childSpeech);
    }

    public List<ChildSpeech> list(String childId) {
        return childSpeechRepository.findAllByChildId(childId);
    }

}
