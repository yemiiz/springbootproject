package com.example.springtest1.controller;

import com.example.springtest1.entity.user;
import com.example.springtest1.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.example.springtest1.common.Result;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    //查询
    @GetMapping
    public Result<List<user>> list() {
        return Result.success(userService.findAll());
    }

    //新增
    @PostMapping
    public Result<user> add(@Valid @RequestBody user newUser){
        return Result.success(userService.addUser(newUser));
    }

    //修改
    @PutMapping("/{id}")
    public Result<user> update(@PathVariable Long id, @Valid @RequestBody user updateUser) {

        return Result.success(userService.updateUser(id, updateUser));
    }

    //删除
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id){
        userService.deleteUser(id);
        return Result.success();
    }
}
