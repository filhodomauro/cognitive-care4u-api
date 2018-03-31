package com.cognitivecare4u.cognitiveapi.images;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildImageRepository extends MongoRepository<ChildImage, String> {
}
