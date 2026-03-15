package org.introspection.backend.controller;

import lombok.RequiredArgsConstructor;
import org.introspection.backend.entity.Entry;
import org.introspection.backend.service.EntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/entries")
public class EntryController {
    private final EntryService entryService;

    @PostMapping
    public ResponseEntity<Entry> create(@RequestBody Entry entry){
        return ResponseEntity.ok(entryService.saveEntry(entry));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Entry>> getByUser(@PathVariable String  userId){
        return ResponseEntity.ok(entryService.getEntriesByUserId(userId));
    }
}
