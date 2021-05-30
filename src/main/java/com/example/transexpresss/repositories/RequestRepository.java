package com.example.transexpresss.repositories;

import com.example.transexpresss.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
