package com.engineeringdigest.journalApp.service;


import com.engineeringdigest.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

// Controller ---> Service ---> Repository
@Component
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
    // No need to explicitly write the code since it's auto-implemented by Spring
    // Spring will add its implementation automatically
}
