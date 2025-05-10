package com.engineeringdigest.journalApp.service;


import com.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

// Controller ---> Service ---> Repository
@Component
public interface UserRepository extends MongoRepository<User, ObjectId> {
    // No need to explicitly write the code since it's auto-implemented by Spring
    // Spring will add its implementation automatically
    User findByUserName(String username);
}
