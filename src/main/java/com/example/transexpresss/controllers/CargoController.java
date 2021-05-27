package com.example.transexpresss.controllers;


import com.example.transexpresss.services.CargoService;
import com.example.transexpresss.services.PaymentService;
import com.example.transexpresss.services.RouteService;
import com.example.transexpresss.services.ShipperService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CargoController {

    private static final Logger logger = LogManager.getLogger(CargoController.class);

    private CargoService cargoService;
    private ShipperService shipperService;
    private PaymentService paymentService;
    private RouteService routeService;

    public CargoController(CargoService cargoService, ShipperService shipperService, PaymentService paymentService, RouteService routeService) {
        this.cargoService = cargoService;
        this.shipperService = shipperService;
        this.paymentService = paymentService;
        this.routeService = routeService;
    }

    @GetMapping("/")
    public String index(Model model) {
        logger.info("");
        return "index";
    }


}
