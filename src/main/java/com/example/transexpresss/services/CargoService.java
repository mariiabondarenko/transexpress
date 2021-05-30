package com.example.transexpresss.services;

import com.example.transexpresss.entities.Cargo;
import com.example.transexpresss.repositories.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Optional<Cargo> getCargoById(Long id) {
        return cargoRepository.findById(id);
    }

    public List<Cargo> getCargoByWeight(Float weight) {
        return getAllCargoes().stream()
                .filter(cargo -> cargo.getWeight() <= weight)
                .collect(Collectors.toList());
    }

    public List<Cargo> getCargoByCapacity(Float capacity) {
        return getAllCargoes().stream()
                .filter(cargo -> cargo.getCapacity() <= capacity)
                .collect(Collectors.toList());
    }

    public List<Cargo> getCargoByLength(Float length) {
        return getAllCargoes().stream()
                .filter(cargo -> cargo.getLength() <= length)
                .collect(Collectors.toList());
    }

    public List<Cargo> getCargoByWidth(Float width) {
        return getAllCargoes().stream()
                .filter(cargo -> cargo.getWidth() <= width)
                .collect(Collectors.toList());
    }

    public List<Cargo> getCargoByHeight(Float height) {
        return getAllCargoes().stream()
                .filter(cargo -> cargo.getHeight() <= height)
                .collect(Collectors.toList());
    }

    public List<Cargo> getCargoByLoadingType(String loadingType) {
        return getAllCargoes().stream()
                .filter(cargo -> cargo.getLoadingType().toLowerCase().contains(loadingType.toLowerCase()))
                .collect(Collectors.toList());
    }

}
