package com.engineeringdigest.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document( collection = "users_journal_app")
@Data // Lombok annotation for getter and setter
@NoArgsConstructor // required for deceralization : JSON to OJO
public class User {

    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String userName;
    @NonNull
    private String password;
    @DBRef // Creating refference of  JournalEntry inside a users_journal_app Collection
    private List<JournalEntry> journalEntries = new ArrayList<>();

}
