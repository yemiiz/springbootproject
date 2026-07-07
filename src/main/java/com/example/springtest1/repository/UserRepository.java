package com.example.springtest1.repository;

import com.example.springtest1.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<user, Long> {
    user findByName(String name);
}
