package org.introspection.backend.service;

import lombok.RequiredArgsConstructor;
import org.introspection.backend.dto.InsightResponseDTO;
import org.introspection.backend.entity.Entry;
import org.introspection.backend.entity.HabitLog;
import org.introspection.backend.entity.Insight;
import org.introspection.backend.entity.Mood;
import org.introspection.backend.repository.EntryRepository;
import org.introspection.backend.repository.HabitLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsightService {

    private final HabitLogRepository habitLogRepository;
    private final EntryRepository entryRepository;

    public InsightResponseDTO generateWeeklyInsight(String userId, LocalDate weekStart) {

        LocalDate weekEnd = weekStart.plusDays(6);

        List<Entry> entries = entryRepository
                .findByUserIdAndDateBetween(userId, weekStart, weekEnd);

        List<HabitLog> logs = habitLogRepository
                .findByUserIdAndDateBetween(userId, weekStart, weekEnd);

        // -------------------------------
        // 1. Dominant Mood
        // -------------------------------
        Map<Mood, Long> moodCount = entries.stream()
                .filter(e -> e.getMood() != null)
                .collect(Collectors.groupingBy(Entry::getMood, Collectors.counting()));

        Mood dominantMood = moodCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        // -------------------------------
        // 2. Average Stress
        // -------------------------------
        double avgStressLevel = entries.stream()
                .mapToInt(Entry::getStressLevel)
                .average()
                .orElse(0.0);

        // -------------------------------
        // 3. Habit Completion Rate
        // -------------------------------
        long totalLogs = logs.size();

        long completed = logs.stream()
                .filter(HabitLog::isCompleted)
                .count();

        double completionRate = totalLogs == 0 ? 0 :
                (double) completed / totalLogs;

        // -------------------------------
        // 4. Top Stress Triggers
        // -------------------------------
        Map<String, Long> triggerCount = new HashMap<>();

        for (Entry entry : entries) {
            if (entry.getStressLevel() >= 7) {

                if (entry.getTags() != null) {
                    for (String tag : entry.getTags()) {
                        triggerCount.put(tag, triggerCount.getOrDefault(tag, 0L) + 1);
                    }
                }

                if (entry.getActivities() != null) {
                    for (String act : entry.getActivities()) {
                        triggerCount.put(act, triggerCount.getOrDefault(act, 0L) + 1);
                    }
                }
            }
        }

        List<String> topTriggers = triggerCount.entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();

        // -------------------------------
        // 5. Correlation (Habit vs Mood)
        // -------------------------------
        Map<LocalDate, Mood> moodByDate = entries.stream()
                .filter(e -> e.getDate() != null && e.getMood() != null)
                .collect(Collectors.toMap(
                        Entry::getDate,
                        Entry::getMood,
                        (existing, replacement) -> existing
                ));

        int completedMoodSum = 0;
        int completedDays = 0;
        int missedMoodSum = 0;
        int missedDays = 0;

        for (HabitLog log : logs) {
            Mood mood = moodByDate.get(log.getDate());
            if (mood == null) continue;

            int moodScore = mood.ordinal();

            if (log.isCompleted()) {
                completedMoodSum += moodScore;
                completedDays++;
            } else {
                missedMoodSum += moodScore;
                missedDays++;
            }
        }

        double completedAvg = completedDays == 0 ? 0 :
                (double) completedMoodSum / completedDays;

        double missedAvg = missedDays == 0 ? 0 :
                (double) missedMoodSum / missedDays;

        double correlationScore = completedAvg - missedAvg;

        Map<String, Double> correlation = Map.of(
                "habit_vs_mood", round(correlationScore)
        );

        // -------------------------------
        // 6. Summary
        // -------------------------------
        String summary = "Your dominant mood was " + dominantMood +
                ", average stress level was " + round(avgStressLevel) +
                ", and habit completion rate was " + round(completionRate * 100) + "%.";

        // -------------------------------
        // 7. Build Insight Object
        // -------------------------------
        Insight insight = new Insight();

        insight.setWeekStart(weekStart);
        insight.setWeekEnd(weekEnd);
        insight.setDominantMood(dominantMood);
        insight.setAvgStressLevel(round(avgStressLevel));
        insight.setHabitCompRate(round(completionRate));
        insight.setTopStressTrigger(topTriggers);
        insight.setCorrelation(correlation);
        insight.setSummaryText(summary);

        return mapToDTO(insight);
    }

    // -------------------------------
    // DTO Mapper
    // -------------------------------
    private InsightResponseDTO mapToDTO(Insight insight) {
        return InsightResponseDTO.builder()
                .weekStart(insight.getWeekStart())
                .weekEnd(insight.getWeekEnd())
                .dominantMood(insight.getDominantMood() != null
                        ? insight.getDominantMood().name()
                        : null)
                .avgStressLevel(insight.getAvgStressLevel())
                .habitCompletionRate(insight.getHabitCompRate() * 100)
                .correlation(insight.getCorrelation())
                .topStressTriggers(insight.getTopStressTrigger())
                .summaryText(insight.getSummaryText())
                .build();
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}