package com.example.transexpresss.controllers;


import com.example.transexpresss.entities.Cargo;
import com.example.transexpresss.entities.Payment;
import com.example.transexpresss.entities.Route;
import com.example.transexpresss.entities.Shipper;
import com.example.transexpresss.services.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class CargoController {

    private static final Logger logger = LogManager.getLogger(CargoController.class);

    private final CargoService cargoService;
    private final ShipperService shipperService;
    private final PaymentService paymentService;
    private final RouteService routeService;

    public CargoController(CargoService cargoService, ShipperService shipperService, PaymentService paymentService, RouteService routeService) {
        this.cargoService = cargoService;
        this.shipperService = shipperService;
        this.paymentService = paymentService;
        this.routeService = routeService;
    }

    //TODO all transports + to UserController
    @GetMapping("/")
    public String index(Model model) {
        logger.info("Loading all cargoes from database");
        model.addAttribute("cargoes", cargoService.getAllCargoes());
        return "index";
    }

    // TODO add shipper
    @GetMapping("/shipper/add_cargo")
    public String addCargo(Model model){
        logger.info("Loading a shipper/add_cargo site");
        model.addAttribute("route",new Route());
        model.addAttribute("payment", new Payment());
        model.addAttribute("cargo",new Cargo());
        return "/shipper/add_cargo";
    }

    // TODO add shipper
    @PostMapping("/shipper/add_cargo")
    public String addCargoSubmit(@Valid Route route, @Valid Payment payment,@Valid Cargo cargo ,BindingResult result) {
        if(result.hasErrors()){
            logger.info("Not valid objects try to add in database");
            return "shipper/add_cargo";
        } else {
            Shipper shipper = new Shipper();
            shipper.setName("test");
            shipperService.saveShipper(shipper);
            cargo.setRoute(route);
            cargo.setPayment(payment);
            cargo.setShipper(shipper);
            logger.info("Add a Route object in database");
            routeService.saveRoute(route);
            logger.info("Add a Payment object in database");
            paymentService.savePayment(payment);
            logger.info("Add a Cargo object in database");
            cargoService.saveCargo(cargo);
            return "redirect:/";
        }
    }

    @GetMapping("/cargoes")
    public String showCargoes(Model model){
        logger.info("Open view for searching cargoes and add all the cargoes");
        model.addAttribute("cargoes", cargoService.getAllCargoes());
        return "search_cargo";
    }

    @GetMapping("/{id}/one_cargo")
    public String showCargoById(@PathVariable String id, Model model) {
        logger.info("Open view for one cargo by id");
        cargoService.getCargoById(Long.valueOf(id)).ifPresent(o -> model.addAttribute("cargo", o));
        return "one_cargo";
    }

    @RequestMapping(value = "/search_cargo")
    public String findCargoByParameter(@RequestParam("value")String value,@RequestParam(value = "type")String type, Model model) {
        switch (type) {
            case "weight": model.addAttribute("cargoes",cargoService.getCargoByWeight(Float.parseFloat(value))); break;
            case "height": model.addAttribute("cargoes",cargoService.getCargoByHeight(Float.parseFloat(value))); break;
            case "width": model.addAttribute("cargoes",cargoService.getCargoByWidth(Float.parseFloat(value))); break;
            case "capacity": model.addAttribute("cargoes",cargoService.getCargoByCapacity(Float.parseFloat(value))); break;
            case "length": model.addAttribute("cargoes",cargoService.getCargoByLength(Float.parseFloat(value))); break;
            case "loading_type" : model.addAttribute("cargoes",cargoService.getCargoByLoadingType(value)); break;
            default: model.addAttribute("cargoes", cargoService.getAllCargoes()); break;
        }
        return "search_cargo";
    }

}
