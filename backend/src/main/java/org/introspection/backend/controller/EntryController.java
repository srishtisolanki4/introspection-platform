package org.introspection.backend.controller;

import lombok.RequiredArgsConstructor;
import org.introspection.backend.dto.EntryRequestDTO;
import org.introspection.backend.dto.EntryResponseDTO;
import org.introspection.backend.entity.Entry;
import org.introspection.backend.service.EntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/entries")
public class EntryController {
    private final EntryService entryService;


    @PostMapping
    public EntryResponseDTO createEntry(@RequestBody EntryRequestDTO dto){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();

        return entryService.createEntry(dto,email);
    }

    @GetMapping()
    public List<EntryResponseDTO> getAllEntries(){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();
        return entryService.getAll(email);
    }

    @PutMapping("/{id}")
    public EntryResponseDTO updateEntry(@PathVariable String id, @RequestBody EntryRequestDTO dto){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();
        return entryService.update(id,email,dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEntry(@PathVariable String id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        entryService.deleteEntry(id, email);

        return ResponseEntity.ok("Entry deleted successfully");
    }

}
