package com.congnitivecare4u.cognitiveapi.children;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildRepository extends MongoRepository<Child, String>{
}
