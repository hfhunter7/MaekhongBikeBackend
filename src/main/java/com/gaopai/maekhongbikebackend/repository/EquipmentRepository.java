package com.gaopai.maekhongbikebackend.repository;

import com.gaopai.maekhongbikebackend.domain.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gaopai.maekhongbikebackend.domain.Equipment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    @Query("select rt.reserveEquipmentPK.equipment from ReserveEquipment rt where rt.reserveEquipmentPK.reserve = :reserve")
    List<Equipment> findByReserve(@Param("reserve") Reserve reserve);
}
