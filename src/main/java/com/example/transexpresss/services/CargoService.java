package com.example.transexpresss.services;

import com.example.transexpresss.entities.Cargo;
import com.example.transexpresss.repositories.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoService {
    private CargoRepository cargoRepository;

    @Autowired
    public void setCargoRepository(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public void saveCargo(Cargo cargo){
        this.cargoRepository.save(cargo);
    }

    public List<Cargo> getAllCargoes(){
        return cargoRepository.findAll();
    }
}
