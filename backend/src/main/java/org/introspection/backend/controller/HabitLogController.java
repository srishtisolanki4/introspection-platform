package org.introspection.backend.controller;


import lombok.RequiredArgsConstructor;
import org.introspection.backend.dto.HabitLogRequestDTO;
import org.introspection.backend.dto.HabitTodayResponseDTO;
import org.introspection.backend.entity.HabitLog;
import org.introspection.backend.entity.User;
import org.introspection.backend.repository.UserRepository;
import org.introspection.backend.service.HabitLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habit-log")
@RequiredArgsConstructor
public class HabitLogController {
    private final HabitLogService habitLogService;
    private final UserRepository  userRepository;

    @PostMapping("/mark")
    public ResponseEntity<HabitLog> markHabit(@RequestBody HabitLogRequestDTO dto){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();
        User user= userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found"));
        String userId=user.getId();
        HabitLog log= habitLogService.markHabit(userId,dto);
        return ResponseEntity.ok(log);

    }

    @GetMapping("/today")
    public ResponseEntity<List<HabitTodayResponseDTO>> getTodayHabits(){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String email= auth.getName();
        User user=userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found"));
        List<HabitTodayResponseDTO> dto=habitLogService.getTodayHabits(user.getId());
        return ResponseEntity.ok(dto);
    }
}
