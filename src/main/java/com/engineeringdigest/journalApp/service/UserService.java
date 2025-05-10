package com.engineeringdigest.journalApp.service;


import com.engineeringdigest.journalApp.entity.JournalEntry;
import com.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// Controller ---> Service ---> Repository
@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveEntry(User user){
            userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }
}
