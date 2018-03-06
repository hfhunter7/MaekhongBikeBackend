package com.gaopai.maekhongbikebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaopai.maekhongbikebackend.domain.Trip;

public interface TripRepository extends JpaRepository<Trip, Long> {

}
