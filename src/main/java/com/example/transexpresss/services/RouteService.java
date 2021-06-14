package com.example.transexpresss.services;

import com.example.transexpresss.entities.Route;
import com.example.transexpresss.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService {
    private RouteRepository routeRepository;

    @Autowired
    public void setRouteRepository(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public void saveRoute(Route route){
        this.routeRepository.save(route);
    }

    public List<Route> getAllRoutes(){
        return this.routeRepository.findAll();
    }

    public List<Route> findRoutesByPoints(String shippingPoint, String deliveryPoint) {
        return getAllRoutes().stream()
                .filter(route -> route.getShippingPoint().toLowerCase().contains(shippingPoint.toLowerCase()))
                .filter(route -> route.getDeliveryPoint().toLowerCase().contains(deliveryPoint.toLowerCase()))
                .collect(Collectors.toList());
    }
}
