package com.example.springtest1.controller;

import org.springframework.web.bind.annotation.*;


@RestController
public class helloController {
    @GetMapping("/hello")
    public String hello(){
        return "hello world~！！！！";
    }

    @GetMapping("/hello/{name}")
    public String say(@PathVariable String name){
        return "hello "+name+"~";
    }

    @GetMapping("/greet")
    public String greet(@RequestParam String name,
                        @RequestParam(defaultValue = "0") int age){
        return "hello "+name+"~ you are "+age+" years old！！";
    }
}
