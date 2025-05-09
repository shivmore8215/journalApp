/*

* Don't remove comment , it is kept for only read purpose
* Created Initially to demonstrate the working of rest API
* Now it will not work because I am going to change the code t work with MongoDB
    (The datatype of id is changed to String so it may create error in whole code)


package com.engineeringdigest.journal.controller;

import com.engineeringdigest.journal.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("journal-array")
public class JournalEntryController {
    private Map<Long, JournalEntry> journalEntries = new HashMap<>();
    @GetMapping("getAll")
    public List<JournalEntry> getAll(){

        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping("add")
    public String createEntry(@RequestBody JournalEntry myEntry){
        journalEntries.put(myEntry.getId(), myEntry);
        return "Added Successfully";
    }

    @GetMapping("dataAt/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable long myId){
        return journalEntries.get(myId);
    }

    @DeleteMapping("delete/{myId}")
    public String deleteJournalEntryById(@PathVariable long myId){
        journalEntries.remove(myId);
        return "DELETED";
    }

    @PutMapping("update/{myId}")
    public String updateJournalEntryById(@PathVariable long myId, @RequestBody JournalEntry myEntry){
        journalEntries.put(myId, myEntry); // if there is no id then the new entry is created
        return "Updated Successfully";
    }
}
*/