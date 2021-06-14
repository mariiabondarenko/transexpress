package com.example.transexpresss.controllers;

import com.example.transexpresss.repositories.CargoRepository;
import com.example.transexpresss.repositories.RequestRepository;
import com.example.transexpresss.repositories.TransportRepository;
import org.springframework.stereotype.Controller;

@Controller
public class RequestController {
    private final RequestRepository requestRepository;
    private final CargoRepository cargoRepository;
    private final TransportRepository transportRepository;

    public RequestController(RequestRepository requestRepository, CargoRepository cargoRepository, TransportRepository transportRepository) {
        this.requestRepository = requestRepository;
        this.cargoRepository = cargoRepository;
        this.transportRepository = transportRepository;
    }




}
