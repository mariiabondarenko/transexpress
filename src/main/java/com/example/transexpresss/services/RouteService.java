package com.example.transexpresss.services;

import com.example.transexpresss.entities.Route;
import com.example.transexpresss.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
