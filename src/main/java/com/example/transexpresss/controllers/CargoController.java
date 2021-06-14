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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CargoController {

    private static final Logger logger = LogManager.getLogger(CargoController.class);

    private final CargoService cargoService;
    private final ShipperService shipperService;
    private final PaymentService paymentService;
    private final RouteService routeService;
    private final TransportService transportService;
    private final RequestService requestService;

    public CargoController(CargoService cargoService, ShipperService shipperService, PaymentService paymentService, RouteService routeService, TransportService transportService, RequestService requestService) {
        this.cargoService = cargoService;
        this.shipperService = shipperService;
        this.paymentService = paymentService;
        this.routeService = routeService;
        this.transportService = transportService;
        this.requestService = requestService;
    }

    @GetMapping("/")
    public String index(Model model) {
        logger.info("Loading all cargoes from database");
        List<Integer> statistic = new ArrayList<>();
        statistic.add(0, shipperService.getAllShippers().size());
        statistic.add(1, transportService.getAllTransports().size());
        statistic.add(2, cargoService.getAllCargoes().size());
        statistic.add(3, requestService.getAllRequests().size());
        model.addAttribute("statistic", statistic);
        model.addAttribute("cargoes", cargoService.getAllCargoes());
        return "index";
    }
    /*
    @GetMapping("/")
    public String index(Model model) {
        return "test";
    }*/

    @GetMapping("/shipper/add_cargo")
    public String addCargo(Model model){
        logger.info("Loading a shipper/add_cargo site");
        model.addAttribute("route",new Route());
        model.addAttribute("payment", new Payment());
        model.addAttribute("cargo",new Cargo());
        return "/shipper/add_cargo";
    }

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

    @RequestMapping(value = "/search_cargo/")
    public String findCargoByPoints(@RequestParam("shippingPoint")String shippingPoint,@RequestParam("deliveryPoint")String deliveryPoint, Model model) {
        logger.info("Search cargoes by points");
        model.addAttribute("cargoes", routeService.findRoutesByPoints(shippingPoint,deliveryPoint)
                                                    .stream()
                                                            .map(Route::getCargo)
                                                            .collect(Collectors.toList()));
        return "search_cargo";
    }

    @GetMapping("/price")
    public String loadPricePage(Model model) {
        model.addAttribute("price","");
        return "price";
    }

    @RequestMapping(value = "/calculate/")
    public String calculatePrice(@RequestParam("distance")String distance,@RequestParam("weight")String weight,@RequestParam("capacity")String capacity, Model model) {
        Float price = Float.parseFloat(distance) + Float.parseFloat(weight) + Float.parseFloat(capacity);
        model.addAttribute("price",price + " грн.");
        return "price";
    }


    @GetMapping("/registration")
    public String loadRegistration() {
        return "register";
    }

    @GetMapping("/shipper_profile")
    public String loadShipperProfile(Model model) {
        shipperService.getShipperById(Long.valueOf("29")).ifPresent(o -> model.addAttribute("shipper", o));
        return "/shipper/shipper_profile";
    }

}
