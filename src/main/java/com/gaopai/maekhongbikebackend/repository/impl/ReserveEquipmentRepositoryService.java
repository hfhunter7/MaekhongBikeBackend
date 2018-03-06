package com.gaopai.maekhongbikebackend.repository.impl;

import com.gaopai.maekhongbikebackend.domain.Reserve;
import com.gaopai.maekhongbikebackend.domain.ReserveEquipment;
import com.gaopai.maekhongbikebackend.repository.ReserveEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReserveEquipmentRepositoryService {

    @Autowired
    private ReserveEquipmentRepository reserveEquipmentRepository;

    public ReserveEquipmentRepositoryService() {

    }

    public ReserveEquipment save(ReserveEquipment reserveEquipment) {
        return reserveEquipmentRepository.save(reserveEquipment);
    }

    public void update(ReserveEquipment reserveEquipment) {
        reserveEquipmentRepository.save(reserveEquipment);
    }

    public void update(List<ReserveEquipment> reserveEquipmentList) {
        reserveEquipmentRepository.save(reserveEquipmentList);
    }

    public void delete(Long id) {
        reserveEquipmentRepository.delete(id);
    }

    public void delete(ReserveEquipment reserveEquipment) {
        reserveEquipmentRepository.delete(reserveEquipment);
    }

    public ReserveEquipment find(Long id) {
        return reserveEquipmentRepository.findOne(id);
    }

    public List<ReserveEquipment> findAll() {
        return reserveEquipmentRepository.findAll();
    }

    public void save(List<ReserveEquipment> reserveEquipments) {
        reserveEquipmentRepository.save(reserveEquipments);
    }

    public List<ReserveEquipment> findByReserve(Reserve reserve){
        return reserveEquipmentRepository.findByReserve(reserve);
    }

    public void deleteReserveEquipment(Long reserve_id , Long equipment_id){
        reserveEquipmentRepository.deleteReserveEquipment(reserve_id , equipment_id);
    }
}
