package com.cognitivecare4u.cognitiveapi.images;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildImageRepository extends MongoRepository<ChildImage, String> {

    List<ChildImage> findAllByChildId(String childId);
}
