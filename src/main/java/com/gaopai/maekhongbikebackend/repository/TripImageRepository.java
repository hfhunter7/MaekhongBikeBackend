package com.gaopai.maekhongbikebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaopai.maekhongbikebackend.domain.TripImage;

import java.util.List;

public interface TripImageRepository extends JpaRepository<TripImage, Long> {

    List<TripImage> findByTrip_Id(Long trip_id);
}
