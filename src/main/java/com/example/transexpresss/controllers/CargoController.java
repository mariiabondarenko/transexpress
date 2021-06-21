package com.example.transexpresss.controllers;

import com.example.transexpresss.entities.Cargo;
import com.example.transexpresss.entities.Payment;
import com.example.transexpresss.entities.Route;
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
            cargo.setRoute(route);
            cargo.setPayment(payment);
            shipperService.getShipperById(Long.valueOf("29")).ifPresent(cargo::setShipper);
            logger.info("Add a Route object in database");
            routeService.saveRoute(route);
            logger.info("Add a Payment object in database");
            paymentService.savePayment(payment);
            logger.info("Add a Cargo object in database");
            cargoService.saveCargo(cargo);
            return "redirect:/shipper_profile";
        }
    }

    @GetMapping("/cargoes")
    public String showCargoes(Model model){
        logger.info("Open view for searching cargoes and add all the cargoes");
        model.addAttribute("cargoes", cargoService.getAllCargoes());
        return "search_cargo";
    }

    @GetMapping("/shipper/cargoes")
    public String showShipperCargoes(Model model){
        logger.info("Open view for searching cargoes and add all the cargoes");
        model.addAttribute("cargoes", cargoService.getAllCargoes());
        return "/shipper/search_cargo";
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
    @GetMapping("/shipper/price")
    public String loadShipperPricePage(Model model) {
        model.addAttribute("price","");
        return "/shipper/price";
    }

    @RequestMapping(value = "/calculate/")
    public String calculatePrice(@RequestParam("distance")String distance,@RequestParam("weight")String weight,@RequestParam("capacity")String capacity, Model model) {
        float calculateDistance = Float.parseFloat(distance);
        float physicalWeight = Float.parseFloat(weight);
        if (physicalWeight < 0.25){
            physicalWeight = Float.parseFloat(capacity) * 0.25F;
        }
        float price;
        if (calculateDistance < 50){
            if(physicalWeight < 5){
                price = calculateDistance * 25;
            }else{
                price = calculateDistance * 40;
            }
        }else{
            if(physicalWeight < 5){
                price = calculateDistance * 15;
            }else{
                price = calculateDistance * 20;
            }
        }
        model.addAttribute("price",price + " грн.");
        return "price";
    }

    @RequestMapping(value = "/shipper/calculate/")
    public String calculateShipperPrice(@RequestParam("distance")String distance,@RequestParam("weight")String weight,@RequestParam("capacity")String capacity, Model model) {
        float calculateDistance = Float.parseFloat(distance);
        float physicalWeight = Float.parseFloat(weight);
        if (physicalWeight < 0.25){
            physicalWeight = Float.parseFloat(capacity) * 0.25F;
        }
        float price;
        if (calculateDistance < 50){
            if(physicalWeight < 5){
                price = calculateDistance * 25;
            }else{
                price = calculateDistance * 40;
            }
        }else{
            if(physicalWeight < 5){
                price = calculateDistance * 15;
            }else{
                price = calculateDistance * 20;
            }
        }
        model.addAttribute("price",price + " грн.");
        return "/shipper/price";
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

    @GetMapping("/enter")
    public String loadEnter(@RequestParam("inputEmail")String inputEmail) {
        if (inputEmail.equals("petr_bond95@gmail.com")){
            return "redirect:/shipper_index";
        }else{
            return "redirect:/carrier_index";
        }
    }

    @GetMapping("/shipper_index")
    public String loadEnterShipper(Model model) {
        model.addAttribute("cargoes", cargoService.getAllCargoes());
        return "/shipper/shipper_index";
    }

    @GetMapping("/carrier_index")
    public String loadEnterCarrier(Model model) {
        model.addAttribute("cargoes", cargoService.getAllCargoes());
        return "/carrier/carrier_index";
    }

    @GetMapping("/shipper/{id}/one_cargo")
    public String showShipperCargoById(@PathVariable String id, Model model) {
        logger.info("Open view for one cargo by id");
        cargoService.getCargoById(Long.valueOf(id)).ifPresent(o -> model.addAttribute("cargo", o));
        return "/shipper/one_cargo";
    }

}
