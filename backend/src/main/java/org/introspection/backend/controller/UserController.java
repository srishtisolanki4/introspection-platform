package org.introspection.backend.controller;

import jakarta.validation.Valid;
import org.introspection.backend.dto.UserRequestDTO;
import org.introspection.backend.dto.UserResponseDTO;
import org.introspection.backend.entity.User;
import org.introspection.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private UserResponseDTO mapToDTO(User user){
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDTO dto){
        try{
        User user=userService.createUser(dto);
        return ResponseEntity.ok(mapToDTO(user));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<UserResponseDTO> findAll(){
        return userService.getAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{userName}")
    public ResponseEntity<UserResponseDTO> getByUserName(@PathVariable String userName){
        return userService.findByUserName(userName).map(user ->
                ResponseEntity.ok(mapToDTO(user))).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userName}")
    public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UserRequestDTO request, @PathVariable String email){
        Optional<User> updatedUser=userService.updateUser(email,request);
        return updatedUser.map(user->ResponseEntity.ok(mapToDTO(user))).orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteUser(Authentication authentication) {
        String email = authentication.getName(); // comes from JWT
        userService.deleteUserByEmail(email);
        return ResponseEntity.ok("User deleted successfully");
    }


}
