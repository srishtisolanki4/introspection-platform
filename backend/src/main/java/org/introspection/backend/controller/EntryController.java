package org.introspection.backend.controller;

import jakarta.validation.Valid;
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
    public ResponseEntity<?> createEntry(@Valid @RequestBody EntryRequestDTO dto, Authentication authentication){
        String email=authentication.getName();

        return new ResponseEntity<>(entryService.createEntry(dto, email), HttpStatus.CREATED);
    }

    @GetMapping()
    public List<EntryResponseDTO> getAllEntries(Authentication authentication){
        String email=authentication.getName();
        return entryService.getAll(email);
    }

    @PutMapping("/{id}")
    public EntryResponseDTO updateEntry(@PathVariable String id, @Valid @RequestBody EntryRequestDTO dto,Authentication authentication){

        String email=authentication.getName();
        return entryService.update(id,email,dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable String id,Authentication authentication){

        String email = authentication.getName();

        entryService.deleteEntry(id, email);

        return ResponseEntity.noContent().build(); // 204
    }

}
