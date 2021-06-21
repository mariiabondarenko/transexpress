package com.example.transexpresss.controllers;

import com.example.transexpresss.entities.Carrier;
import com.example.transexpresss.entities.Route;
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
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class TransportController {
    private static final Logger logger = LogManager.getLogger(TransportController.class);

    private final TransportService transportService;
    private final CarrierService carrierService;

    public TransportController(TransportService transportService, CarrierService carrierService) {
        this.transportService = transportService;
        this.carrierService = carrierService;
    }

    @GetMapping("/carrier/add_transport")
    public String addTransport(Model model){
        logger.info("Loading a carrier/add_transport site");
        model.addAttribute("transport",new Transport());
        return "carrier/add_transport";
    }

    @PostMapping("/carrier/add_transport")
    public String addTransportSubmit(@Valid Transport transport, BindingResult result) {
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
    @GetMapping("/shipper/transports")
    public String showShipperTransports(Model model){
        logger.info("Open view for searching transports and add all transports");
        model.addAttribute("transports", transportService.getAllTransports());
        return "/shipper/search_transport";
    }

    @GetMapping("/{id}/one_transport")
    public String showTransportById(@PathVariable String id, Model model) {
        logger.info("Open view for one transport by id");
        transportService.getTransportById(Long.valueOf(id)).ifPresent(o -> model.addAttribute("transport", o));
        return "one_transport";
    }

    @RequestMapping(value = "/{id}/del_transport")
    public String delTransportById(@PathVariable(value = "id") long id){
        transportService.deleteTransportById(id);
        return "redirect:/carrier_profile";
    }

    @RequestMapping(value = "/search_transport/")
    public String findCargoByPoints(@RequestParam("carrying")String carrying,@RequestParam("capacity")String capacity, Model model) {
        logger.info("Search transports by param");
        List<Transport> list_carrying = transportService.getTransportByCarrying(Float.parseFloat(carrying));
        List<Transport> list_capacity = transportService.getTransportByCapacity(Float.parseFloat(capacity));
        Set<Transport> transportSet = new LinkedHashSet<>(list_carrying);
        transportSet.addAll(list_capacity);
        List<Transport> finalList = new ArrayList<>(transportSet);
        model.addAttribute("transports", finalList);
        return "search_transport";
    }

    @GetMapping("/carrier_profile")
    public String loadCarrierProfile(Model model) {
        carrierService.getCarrierById(Long.valueOf("1")).ifPresent(o -> model.addAttribute("carrier", o));
        return "/carrier/carrier_profile";
    }

    @GetMapping("/shipper/{id}/one_transport")
    public String showTransportByIdForShipper(@PathVariable String id, Model model) {
        logger.info("Open view for one transport by id");
        transportService.getTransportById(Long.valueOf(id)).ifPresent(o -> model.addAttribute("transport", o));
        return "/shipper/one_transport";
    }
}
