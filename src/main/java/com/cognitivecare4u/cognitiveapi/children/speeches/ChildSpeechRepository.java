package com.cognitivecare4u.cognitiveapi.children.speeches;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildSpeechRepository extends MongoRepository<ChildSpeech, String> {
}
