package org.introspection.backend.service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.introspection.backend.dto.HabitLogRequestDTO;
import org.introspection.backend.dto.HabitTodayResponseDTO;
import org.introspection.backend.entity.Habit;
import org.introspection.backend.entity.HabitLog;
import org.introspection.backend.repository.HabitLogRepository;
import org.introspection.backend.repository.HabitRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HabitLogService {

    private final HabitLogRepository habitLogRepository;
    private final HabitRepository habitRepository;

    public HabitLog markHabit(String userId, HabitLogRequestDTO dto) {
        LocalDate today = LocalDate.now();

        Optional<HabitLog> habit = habitLogRepository.findByUserIdAndHabitIdAndDate(
                userId, dto.getHabitId(), today
        );

        if (habit.isPresent()) {
            HabitLog old = habit.get();
            old.setCompleted(dto.isCompleted());
            old.setMoodPostHabit(dto.getMoodPostHabit());
            return habitLogRepository.save(old);
        } else {
            HabitLog newLog = new HabitLog();
            newLog.setUserId(userId);
            newLog.setHabitId(dto.getHabitId());
            newLog.setDate(today);
            newLog.setCompleted(dto.isCompleted());
            newLog.setMoodPostHabit(dto.getMoodPostHabit());

            return habitLogRepository.save(newLog);
        }
    }
    private int calcStreak(String userId, String habitId) {
        List<HabitLog> logs = habitLogRepository.findByUserIdAndHabitIdOrderByDateDesc(userId, habitId);
        int streak = 0;
        LocalDate currDate = LocalDate.now();

        for (HabitLog log : logs) {
            if (!log.getDate().equals(currDate)) {
                break;
            }
            if (!log.isCompleted()) break;

            streak++;
            currDate = currDate.minusDays(1);
        }
        return streak;
    }
        public List<HabitTodayResponseDTO> getTodayHabits(String userId) {
            LocalDate date=LocalDate.now();
            List<Habit> habits=habitRepository.findByUserId(userId);
            List<HabitLog> logs=habitLogRepository.findByUserIdAndDate(userId,date);
            Map<String,HabitLog> logMap=logs.stream()
                    .collect(Collectors.toMap(HabitLog::getHabitId,log->log));
            List<HabitTodayResponseDTO> response=new ArrayList<>();

            for(Habit habit :habits){
                HabitLog log=logMap.get(habit.getId());
                HabitTodayResponseDTO temp=new HabitTodayResponseDTO();
                temp.setHabitId(habit.getId());
                temp.setHabitName(habit.getName());

                if(log!=null){
                    temp.setCompleted(log.isCompleted());
                    temp.setMoodPostHabit(log.getMoodPostHabit());
                    int streak=calcStreak(userId, habit.getId());
                    temp.setStreak(streak);
                }else{
                    temp.setCompleted(false);
                    temp.setMoodPostHabit(null);
                }
                response.add(temp);
            }
            return response;
        }



}
