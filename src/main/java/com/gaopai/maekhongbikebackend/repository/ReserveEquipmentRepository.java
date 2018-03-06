package com.gaopai.maekhongbikebackend.repository;

import com.gaopai.maekhongbikebackend.domain.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gaopai.maekhongbikebackend.domain.ReserveEquipment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReserveEquipmentRepository extends JpaRepository<ReserveEquipment, Long> {
    @Query("select re from ReserveEquipment re where re.reserveEquipmentPK.reserve = :reserve")
    List<ReserveEquipment> findByReserve(@Param("reserve") Reserve reserve);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM reserve_equipment WHERE reserve_id = :reserve_id and equipment_id = :equipment_id" , nativeQuery = true)
    void deleteReserveEquipment(@Param("reserve_id") Long reserve_id , @Param("equipment_id") Long equipment_id);
}
