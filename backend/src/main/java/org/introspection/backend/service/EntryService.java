package org.introspection.backend.service;

import java.util.*;

import lombok.RequiredArgsConstructor;
import org.introspection.backend.dto.EntryRequestDTO;
import org.introspection.backend.dto.EntryResponseDTO;
import org.introspection.backend.entity.Entry;
import org.introspection.backend.entity.User;
import org.introspection.backend.exception.ResourceNotFoundException;
import org.introspection.backend.exception.UnauthorizedException;
import org.introspection.backend.repository.EntryRepository;
import org.introspection.backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EntryService {


    private final EntryRepository entryRepository;


    private final UserRepository userRepository;

    private EntryResponseDTO mapToDTO(Entry saved){
        return EntryResponseDTO.builder().
                id(saved.getId()).
                title(saved.getTitle()).
                content(saved.getContent()).
                energyLevel(saved.getEnergyLevel()).
                createdAt(saved.getCreatedAt()).
                tags(saved.getTags()).
                build();
    }

    public EntryResponseDTO createEntry(EntryRequestDTO request,String email){
        User user=userRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("User does not exist"));
        Entry entry=new Entry();
        entry.setTitle(request.getTitle());
        entry.setContent(request.getContent());
        entry.setMood(request.getMood());
        entry.setEnergyLevel(request.getEnergyLevel());
        entry.setProductivityScore(request.getProductivityScore());
        entry.setSleepHours(request.getSleepHours());
        entry.setStressLevel(request.getStressLevel());
        entry.setActivities(request.getActivities());
        entry.setUserId(user.getId());
        entry.setTags(request.getTags());
        entry.setDate(
                request.getDate() != null ? request.getDate() : LocalDate.now()
        );
        entry.setCreatedAt(LocalDate.now());


        Entry saved=entryRepository.save(entry);

        return mapToDTO(saved);
    }

    public List<EntryResponseDTO> getAll(String email){
        User user=userRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        List<Entry> entries = entryRepository.findByUserId(user.getId());

        return entries.stream()
                .map(this::mapToDTO)
                .toList();

    }

    public EntryResponseDTO update(String id, String email , EntryRequestDTO request){
        User user= userRepository.findByEmail(email).
                orElseThrow(()-> new ResourceNotFoundException("User not found"));

        Entry oldEntry=entryRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Entry not found"));

        if(!oldEntry.getUserId().equals(user.getId())){
            throw new UnauthorizedException("You are not allowed to access this entry");
        }


        if(request.getTitle() != null)
            oldEntry.setTitle(request.getTitle());
        oldEntry.setContent(request.getContent());
        oldEntry.setMood(request.getMood());
        oldEntry.setEnergyLevel(request.getEnergyLevel());
        oldEntry.setProductivityScore(request.getProductivityScore());
        oldEntry.setSleepHours(request.getSleepHours());
        oldEntry.setStressLevel(request.getStressLevel());
        oldEntry.setActivities(request.getActivities());
        oldEntry.setUserId(user.getId());
        oldEntry.setTags(request.getTags());
        oldEntry.setDate(
                request.getDate() != null ? request.getDate() : LocalDate.now()
        );

        Entry saved=entryRepository.save(oldEntry);

        return mapToDTO(saved);
    }

    public void deleteEntry(String id, String email){
        User user= userRepository.findByEmail(email).
                orElseThrow(()-> new ResourceNotFoundException("User not found"));

        Entry oldEntry=entryRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Entry not found"));

        if(!oldEntry.getUserId().equals(user.getId())){
            throw new UnauthorizedException("You are not allowed to access this entry");
        }

        entryRepository.deleteById(id);
    }






}
