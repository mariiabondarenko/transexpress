package com.example.transexpresss.services;

import com.example.transexpresss.entities.Request;
import com.example.transexpresss.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {
    private RequestRepository requestRepository;

    @Autowired
    public void setRequestRepository(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public void saveRequest(Request request){
        this.requestRepository.save(request);
    }

    public List<Request> getAllRequests(){
        return requestRepository.findAll();
    }

    public Optional<Request> getRequestById(Long id) {
        return requestRepository.findById(id);
    }
}
