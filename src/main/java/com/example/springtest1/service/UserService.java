package com.example.springtest1.service;


import com.example.springtest1.dto.LoginDTO;
import com.example.springtest1.entity.user;
import com.example.springtest1.repository.UserRepository;
import com.example.springtest1.util.JwtUtil;
import com.example.springtest1.vo.LoginVO;
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

    public LoginVO login(LoginDTO dto) {
        user u = userRepository.findByName(dto.getName());
        if (u == null || !u.getPassword().equals(dto.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        String token = JwtUtil.generateToken(u.getId());
        long expireTime = System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000L;
        LoginVO vo = new LoginVO();
        vo.setUserId(u.getId());
        vo.setName(u.getName());
        vo.setToken(token);
        vo.setExpireTime(expireTime);
        return vo;
    }
}
