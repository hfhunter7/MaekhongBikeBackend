package com.gaopai.maekhongbikebackend.repository.impl;

import com.gaopai.maekhongbikebackend.domain.TripImage;
import com.gaopai.maekhongbikebackend.repository.TripImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripImageRepositoryService {

    @Autowired
    private TripImageRepository tripImageRepository;

    public TripImageRepositoryService() {

    }

    public TripImage save(TripImage tripImage) {
        return tripImageRepository.save(tripImage);
    }

    public void update(TripImage tripImage) {
        tripImageRepository.save(tripImage);
    }

    public void update(List<TripImage> tripImageList) {
        tripImageRepository.save(tripImageList);
    }

    public void delete(Long id) {
        tripImageRepository.delete(id);
    }

    public void delete(TripImage tripImage) {
        tripImageRepository.delete(tripImage);
    }

    public TripImage find(Long id) {
        return tripImageRepository.findOne(id);
    }

    public List<TripImage> findAll() {
        return tripImageRepository.findAll();
    }

    public List<TripImage> findByTrip_Id(Long trip_id){
        return tripImageRepository.findByTrip_Id(trip_id);
    }
}
