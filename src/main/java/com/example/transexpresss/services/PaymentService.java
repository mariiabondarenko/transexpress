package com.example.transexpresss.services;

import com.example.transexpresss.entities.Payment;
import com.example.transexpresss.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private PaymentRepository paymentRepository;

    @Autowired
    public void setPaymentRepository(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    public void savePayment(Payment payment){
        this.paymentRepository.save(payment);
    }

}
