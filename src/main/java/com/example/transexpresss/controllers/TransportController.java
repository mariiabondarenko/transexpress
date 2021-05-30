package com.example.transexpresss.controllers;

import com.example.transexpresss.entities.Carrier;
import com.example.transexpresss.entities.Transport;
import com.example.transexpresss.services.CarrierService;
import com.example.transexpresss.services.TransportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class TransportController {
    private static final Logger logger = LogManager.getLogger(TransportController.class);

    private final TransportService transportService;
    private final CarrierService carrierService;

    public TransportController(TransportService transportService, CarrierService carrierService) {
        this.transportService = transportService;
        this.carrierService = carrierService;
    }

    //TODO carrier
    @GetMapping("/carrier/add_transport")
    public String addTransport(Model model){
        logger.info("Loading a carrier/add_transport site");
        model.addAttribute("transport",new Transport());
        return "carrier/add_transport";
    }

    //TODO add carrier
    @PostMapping("/carrier/add_transport")
    public String addCargoSubmit(@Valid Transport transport, BindingResult result) {
        if(result.hasErrors()){
            logger.info("Not valid objects try to add in database");
            return "carrier/add_transport";
        } else {
            Carrier carrier = new Carrier();
            carrier.setName("test");
            carrierService.saveCarrier(carrier);
            transport.setCarrier(carrier);
            logger.info("Add a Transport object in database");
            transportService.saveTransport(transport);
            return "redirect:/";
        }
    }


    @GetMapping("/transports")
    public String showTransports(Model model){
        logger.info("Open view for searching transports and add all transports");
        model.addAttribute("transports", transportService.getAllTransports());
        return "search_transport";
    }

    @GetMapping("/{id}/one_transport")
    public String showTransportById(@PathVariable String id, Model model) {
        logger.info("Open view for one transport by id");
        transportService.getTransportById(Long.valueOf(id)).ifPresent(o -> model.addAttribute("transport", o));
        return "one_transport";
    }

    //TODO change map
    @RequestMapping(value = "/{id}/del_transport")
    public String delTransportById(@PathVariable(value = "id") long id){
        transportService.deleteTransportById(id);
        return "redirect:/";
    }

    @RequestMapping(value = "/search_transporto")
    public String findCargoByParameter(@RequestParam("value")String value, @RequestParam(value = "type")String type, Model model) {
        switch (type) {
            case "capacity": model.addAttribute("transports",transportService.getTransportByCapacity(Float.parseFloat(value))); break;
            case "height": model.addAttribute("transports",transportService.getTransportByHeight(Float.parseFloat(value))); break;
            case "width": model.addAttribute("transports",transportService.getTransportByWidth(Float.parseFloat(value))); break;
            case "length": model.addAttribute("transports",transportService.getTransportByLength(Float.parseFloat(value))); break;
            case "carrying" : model.addAttribute("transports",transportService.getTransportByCapacity(Float.parseFloat(value))); break;
            case "loading_type" : model.addAttribute("transports",transportService.getTransportByLoadingType(value)); break;
            case "region" : model.addAttribute("transports",transportService.getTransportByRegion(value)); break;
            default: model.addAttribute("transports", transportService.getAllTransports()); break;
        }
        return "search_cargo";
    }

}
