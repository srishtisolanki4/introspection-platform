package org.introspection.backend.controller;

import lombok.RequiredArgsConstructor;
import org.introspection.backend.dto.InsightResponseDTO;
import org.introspection.backend.entity.User;
import org.introspection.backend.service.InsightService;
import org.introspection.backend.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/insight")
@RequiredArgsConstructor
public class InsightController {

    private final InsightService insightService;
    private final UserService userService;

    @GetMapping("/weekly")
    public InsightResponseDTO getWeeklyInsight(
            @RequestParam String weekStart
    ) {

        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();


        User user = userService.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));

        return insightService.generateWeeklyInsight(
                user.getId(),
                LocalDate.parse(weekStart)
        );
    }
}