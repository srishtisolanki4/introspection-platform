package org.introspection.backend.service;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.introspection.backend.entity.Habit;
import org.introspection.backend.entity.User;
import org.introspection.backend.dto.HabitRequestDTO;
import org.introspection.backend.dto.HabitResponseDTO;
import org.introspection.backend.exception.ResourceNotFoundException;
import org.introspection.backend.exception.UnauthorizedException;
import org.introspection.backend.repository.HabitRepository;
import org.introspection.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HabitService {
    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    private HabitResponseDTO mapToDTO(Habit habit){
        return HabitResponseDTO.builder()
                .id(habit.getId())
                .name(habit.getName())
                .description(habit.getDescription())
                .targetPerWeek(habit.getTargetPerWeek())
                .createdAt(habit.getCreatedAt())
                .build();

    }
    public HabitResponseDTO create(HabitRequestDTO dto,String email){
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));

        Habit habit=new Habit();
        habit.setName(dto.getName());
        habit.setDescription(dto.getDescription());
        habit.setTargetPerWeek(dto.getTargetPerWeek());
        habit.setUserId(user.getId());

        Habit saved=habitRepository.save(habit);
        return mapToDTO(saved);

    }

    public List<HabitResponseDTO> getAll(String email){
        User user=userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found"));

        List<Habit> habits=habitRepository.findByUserId(user.getId());
        return habits.stream().map(this::mapToDTO).toList();
    }

    public HabitResponseDTO update(String id,String email ,HabitRequestDTO dto ){
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));
        Habit oldHabit=habitRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Habit not found"));

        if(!oldHabit.getUserId().equals(user.getId())){
            throw new UnauthorizedException("You are not allowed to add habits");
        }

        oldHabit.setName(dto.getName());
        oldHabit.setDescription(dto.getDescription());
        oldHabit.setTargetPerWeek(dto.getTargetPerWeek());
        Habit updated=habitRepository.save(oldHabit);
        return mapToDTO(updated);


    }

    public void delete(String id,String email ){
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));
        Habit oldHabit=habitRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Habit not found"));

        if(!oldHabit.getUserId().equals(user.getId())){
            throw new UnauthorizedException("You are not allowed to add habits");
        }
        habitRepository.delete(oldHabit);
    }
}
