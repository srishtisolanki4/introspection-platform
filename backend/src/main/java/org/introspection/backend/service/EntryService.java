package org.introspection.backend.service;

import java.util.*;

import lombok.RequiredArgsConstructor;
import org.introspection.backend.entity.Entry;
import org.introspection.backend.repository.EntryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EntryService {
    private final EntryRepository entryRepository;



    public Entry saveEntry(Entry entry){
        System.out.println("💾 Saving entry...");
        if(entry.getCreatedAt()==null){
            entry.setCreatedAt(LocalDateTime.now());
        }
        return entryRepository.save(entry);
    }

    @GetMapping("/all")
    public List<Entry> getAll() {
        List<Entry> entries = entryRepository.findAll();
        System.out.println("Entries count: " + entries.size());
        return entries;
    }

    public List<Entry> getEntriesByUserId(String userId){

        return entryRepository.findByUserId(userId);
    }

}
