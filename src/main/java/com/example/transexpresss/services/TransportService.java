package com.example.transexpresss.services;

import com.example.transexpresss.entities.Cargo;
import com.example.transexpresss.entities.Transport;
import com.example.transexpresss.repositories.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransportService {
    private TransportRepository transportRepository;

    @Autowired
    public void setTransportRepository(TransportRepository transportRepository) {
        this.transportRepository = transportRepository;
    }

    public void saveTransport(Transport transport){
        this.transportRepository.save(transport);
    }

    public List<Transport> getAllTransports(){
        return transportRepository.findAll();
    }

    public Optional<Transport> getTransportById(Long id) {
        return transportRepository.findById(id);
    }

    public void deleteTransportById(Long id){
        transportRepository.deleteById(id);
    }

    public List<Transport> getTransportByCarrying(Float carrying) {
        return getAllTransports().stream()
                .filter(transport -> transport.getCarrying() >= carrying)
                .collect(Collectors.toList());
    }

    public List<Transport> getTransportByCapacity(Float capacity) {
        return getAllTransports().stream()
                .filter(transport -> transport.getCapacity() >= capacity)
                .collect(Collectors.toList());
    }

    public List<Transport> getTransportByLength(Float length) {
        return getAllTransports().stream()
                .filter(transport -> transport.getLength() >= length)
                .collect(Collectors.toList());
    }

    public List<Transport> getTransportByWidth(Float width) {
        return getAllTransports().stream()
                .filter(transport -> transport.getWidth() >= width)
                .collect(Collectors.toList());
    }

    public List<Transport> getTransportByHeight(Float height) {
        return getAllTransports().stream()
                .filter(transport -> transport.getHeight() >= height)
                .collect(Collectors.toList());
    }

    public List<Transport> getTransportByLoadingType(String loadingType) {
        return getAllTransports().stream()
                .filter(transport -> transport.getLoadingType().toLowerCase().contains(loadingType.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Transport> getTransportByRegion(String region) {
        return getAllTransports().stream()
                .filter(transport -> transport.getRegion().toLowerCase().contains(region.toLowerCase()))
                .collect(Collectors.toList());
    }

}
