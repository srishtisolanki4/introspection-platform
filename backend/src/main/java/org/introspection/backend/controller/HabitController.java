package org.introspection.backend.controller;

import lombok.RequiredArgsConstructor;
import org.introspection.backend.dto.HabitRequestDTO;
import org.introspection.backend.dto.HabitResponseDTO;
import org.introspection.backend.repository.HabitRepository;
import org.introspection.backend.repository.UserRepository;
import org.introspection.backend.service.HabitService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/habits")
public class HabitController {

    private final HabitService habitService;

    @PostMapping
    public HabitResponseDTO createHabit(@RequestBody HabitRequestDTO dto){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String email= auth.getName();
        return habitService.create(dto,email);
    }

    @GetMapping
    public List<HabitResponseDTO> getAll(){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String email= auth.getName();
        return habitService.getAll(email);
    }

    @PutMapping("/{id}")
    public HabitResponseDTO update(@PathVariable String id, @RequestBody HabitRequestDTO dto){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String email= auth.getName();
        return habitService.update(id,email,dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String email= auth.getName();
        habitService.delete(id,email);
        return ResponseEntity.ok("Habit deleted successfully");
    }
}
