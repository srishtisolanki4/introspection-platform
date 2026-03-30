package org.introspection.backend.service;

import java.util.*;

import com.mongodb.DuplicateKeyException;
import org.introspection.backend.dto.UserRequestDTO;
import org.introspection.backend.entity.User;
import org.introspection.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

@Service
public class UserService {

    @Autowired
        private UserRepository userRepository;
    @Autowired
        private PasswordEncoder passwordEncoder;

    public User createUser(UserRequestDTO dto){
        User user=new User();
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        try {
            if (userRepository.existsByUserName(user.getUserName())) {
                throw new RuntimeException("Username already exists");
            }
            return userRepository.save(user);
        } catch (DuplicateKeyException e) {
            throw new RuntimeException("Username or email already exists");
        }
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void deleteById(String userId){
        userRepository.deleteById(userId);
    }

    public Optional<User> updateUser(String email, UserRequestDTO dto){
        Optional<User> old=findByEmail(email);
        if(old.isPresent()){
            User user= old.get();
            user.setUserName(dto.getUserName());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            return Optional.of(userRepository.save(user));
        }else{
            return Optional.empty();
        }
    }

    public void deleteUserByEmail(String email) {
        userRepository.deleteByEmail(email);
    }


}
