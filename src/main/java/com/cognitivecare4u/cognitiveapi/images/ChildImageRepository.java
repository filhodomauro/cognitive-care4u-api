package com.cognitivecare4u.cognitiveapi.images;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChildImageRepository extends MongoRepository<ChildImage, String> {

    List<ChildImage> findAllByChildId(String childId);

    Optional<ChildImage> findByIdAndChildId(String id, String childId);
}
