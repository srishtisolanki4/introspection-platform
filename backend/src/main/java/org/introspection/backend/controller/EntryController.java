package org.introspection.backend.controller;

import lombok.RequiredArgsConstructor;
import org.introspection.backend.entity.Entry;
import org.introspection.backend.service.EntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/entries")
public class EntryController {
    private final EntryService entryService;


    @PostMapping
    public ResponseEntity<Entry> create(@RequestBody Entry entry){
        System.out.println("🔥 API HIT");
        return ResponseEntity.ok(entryService.saveEntry(entry));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Entry>> getByUser(@PathVariable String  userId){
        Optional<List<Entry>> user= Optional.ofNullable(entryService.getEntriesByUserId(userId));
        if(user.isPresent()){
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
