package com.example.transexpresss.repositories;

import com.example.transexpresss.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
