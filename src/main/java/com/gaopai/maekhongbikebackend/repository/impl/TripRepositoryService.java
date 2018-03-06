package com.gaopai.maekhongbikebackend.repository.impl;

import com.gaopai.maekhongbikebackend.domain.Trip;
import com.gaopai.maekhongbikebackend.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripRepositoryService {

    @Autowired
    private TripRepository tripRepository;

    public TripRepositoryService() {

    }

    public Trip save(Trip trip) {
        return tripRepository.save(trip);
    }

    public void update(Trip trip) {
        tripRepository.save(trip);
    }

    public void update(List<Trip> tripList) {
        tripRepository.save(tripList);
    }

    public void delete(Long id) {
        tripRepository.delete(id);
    }

    public void delete(Trip trip) {
        tripRepository.delete(trip);
    }

    public Trip find(Long id) {
        return tripRepository.findOne(id);
    }

    public List<Trip> findAll() {
        return tripRepository.findAll();
    }
}
