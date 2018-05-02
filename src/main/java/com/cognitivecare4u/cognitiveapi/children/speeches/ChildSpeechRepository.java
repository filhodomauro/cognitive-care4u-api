package com.cognitivecare4u.cognitiveapi.children.speeches;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildSpeechRepository extends MongoRepository<ChildSpeech, String> {

    List<ChildSpeech> findAllByChildId(String childId);
}
