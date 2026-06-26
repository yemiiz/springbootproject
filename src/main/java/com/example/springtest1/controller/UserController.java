package com.example.springtest1.controller;

import com.example.springtest1.entity.user;
import com.example.springtest1.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.example.springtest1.common.Result;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    //查询
    @GetMapping
    public Result<List<user>> list() {
        return Result.success(userRepository.findAll());
    }

    //新增
    @PostMapping
    public Result<user> add(@Valid @RequestBody user newUser){
        return Result.success(userRepository.save(newUser));
    }

    //修改
    @PutMapping("/{id}")
    public Result<user> update(@PathVariable Long id, @Valid @RequestBody user updateUser) {
        user existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        existing.setEmail(updateUser.getEmail());
        existing.setName(updateUser.getName());
        existing.setAge(updateUser.getAge());
        return Result.success(userRepository.save(existing));
    }

    //删除
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id){
        user existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.deleteById(id);
        //userRepository.delete(existing);
        return Result.success();
    }
}
