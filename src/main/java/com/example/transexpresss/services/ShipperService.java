package com.example.transexpresss.services;

import com.example.transexpresss.entities.Shipper;
import com.example.transexpresss.repositories.ShipperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipperService {
    private ShipperRepository shipperRepository;

    @Autowired
    public void setShipperRepository(ShipperRepository shipperRepository) {
        this.shipperRepository = shipperRepository;
    }

    public void saveShipper(Shipper shipper){
        this.shipperRepository.save(shipper);
    }

    public List<Shipper> getAllShippers(){
        return shipperRepository.findAll();
    }

    public Optional<Shipper> getShipperById(Long id) {
        return shipperRepository.findById(id);
    }
}
