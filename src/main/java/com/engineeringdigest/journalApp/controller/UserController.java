package com.engineeringdigest.journalApp.controller;

import com.engineeringdigest.journalApp.entity.JournalEntry;
import com.engineeringdigest.journalApp.entity.User;
import com.engineeringdigest.journalApp.service.JournalEntryService;
import com.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Controller ---> Service ---> Repository
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAll();
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.saveEntry(user);
    }

    @PutMapping("/{userName}/{password}")
    public ResponseEntity<?> updateUser(@RequestBody User user,
                                        @PathVariable String userName,
                                        @PathVariable String password){
        User userInDb = userService.findByUserName(userName);
        if(userInDb != null){
            if(userInDb.getPassword().equals(password)){
                userInDb.setUserName(user.getUserName());
                userInDb.setPassword(user.getPassword());
                userService.saveEntry(userInDb);
                return new ResponseEntity<>("Credential Updated Successfully !", HttpStatus.OK);
            }
            return new ResponseEntity<>("Wrong Password !", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User Not Found !", HttpStatus.NOT_FOUND);
    }
}
