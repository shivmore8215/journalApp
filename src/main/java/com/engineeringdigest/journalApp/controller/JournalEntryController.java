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
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName){
        try {
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        List<JournalEntry> allData = user.getJournalEntries();

        if(allData!= null && ! allData.isEmpty()) {
            return new ResponseEntity<>(allData, HttpStatus.OK);
        }
        return new ResponseEntity<>("Collection is Empty !",HttpStatus.NO_CONTENT);
    }

    @GetMapping("dataAt/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Id Not Found !", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("delete/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable String userName,
                                                    @PathVariable ObjectId myId){

        JournalEntry oldEntry =  journalEntryService.findById(myId).orElse(null);
        if(oldEntry != null) {
            journalEntryService.deleteById(myId, userName);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Id Not Found !", HttpStatus.NOT_FOUND);
    }

    @PutMapping("update/{userName}/{myId}")
    public ResponseEntity<?> updateJournalEntryById(
            @PathVariable ObjectId myId,
            @PathVariable String userName,
            @RequestBody JournalEntry newEntry){
        JournalEntry oldEntry = journalEntryService.findById(myId).orElse(null);
        if(oldEntry != null){
            oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldEntry.getContent());
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }
        /*
        The object in memory is now disconnected from the database at this point.
        ** -->      oldEntry.setTitle(...);
                    oldEntry.setContent(...);

        Save to persist changes:
                    To apply those modified fields and persist them back to the database, you need to explicitly call:
        ** -->      journalEntryService.saveEntry(oldEntry);

                    Without this step, the database remains unaware of your changes, and the updates won't be permanent.
         */
        return new ResponseEntity<>("Id Not Found !", HttpStatus.NOT_FOUND);
    }
}
