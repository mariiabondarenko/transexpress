package com.example.transexpresss.repositories;

import com.example.transexpresss.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
