package com.gaopai.maekhongbikebackend.repository.impl;

import com.gaopai.maekhongbikebackend.domain.Equipment;
import com.gaopai.maekhongbikebackend.domain.Reserve;
import com.gaopai.maekhongbikebackend.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentRepositoryService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    public EquipmentRepositoryService() {

    }

    public Equipment save(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    public void update(Equipment equipment) {
        equipmentRepository.save(equipment);
    }

    public void update(List<Equipment> equipmentList) {
        equipmentRepository.save(equipmentList);
    }

    public void delete(Long id) {
        equipmentRepository.delete(id);
    }

    public void delete(Equipment equipment) {
        equipmentRepository.delete(equipment);
    }

    public Equipment find(Long id) {
        return equipmentRepository.findOne(id);
    }

    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }

    public List<Equipment> findByReserve(Reserve reserve){
        return equipmentRepository.findByReserve(reserve);
    }
}
