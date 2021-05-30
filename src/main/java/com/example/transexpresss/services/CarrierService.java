package com.example.transexpresss.services;

import com.example.transexpresss.entities.Carrier;
import com.example.transexpresss.repositories.CarrierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarrierService {
    private CarrierRepository carrierRepository;

    @Autowired
    public void setCarrierRepository(CarrierRepository carrierRepository) {
        this.carrierRepository = carrierRepository;
    }

    public void saveCarrier(Carrier carrier){
        this.carrierRepository.save(carrier);
    }

    public List<Carrier> getAllCarriers(){
        return carrierRepository.findAll();
    }

    public Optional<Carrier> getCarrierById(Long id) {
        return carrierRepository.findById(id);
    }
}
