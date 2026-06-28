package com.example.springtest1.service;


import com.example.springtest1.entity.user;
import com.example.springtest1.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<user> findAll(){
        return userRepository.findAll();
    }

    @Transactional
    public user addUser(user newUser){
        return userRepository.save(newUser);
    }

    @Transactional
    public user updateUser(Long id, user updatedUser){
        user existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        existing.setName(updatedUser.getName());
        existing.setAge(updatedUser.getAge());
        existing.setEmail(updatedUser.getEmail());
        return userRepository.save(existing);
    }

    @Transactional
    public void deleteUser(Long id){
        user existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(existing);
    }
}
