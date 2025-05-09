package com.engineeringdigest.journalApp.controller;

import com.engineeringdigest.journalApp.entity.JournalEntry;
import com.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// Controller ---> Service ---> Repository
@RestController
@RequestMapping("journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @PostMapping("add")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try {
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getAll")
    public ResponseEntity<?> getAll(){
        List<JournalEntry> allData = journalEntryService.getAll();
        if(! allData.isEmpty()) {
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

    @DeleteMapping("delete/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId){
        List<JournalEntry> oldEntry = (List<JournalEntry>) journalEntryService.findById(myId).orElse(null);
        if(oldEntry != null) {
            journalEntryService.deleteById(myId);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Id Not Found !", HttpStatus.NOT_FOUND);
    }

    @PutMapping("update/{myId}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
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
