package com.gaopai.maekhongbikebackend.repository;

import com.gaopai.maekhongbikebackend.domain.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ViewRepository  extends JpaRepository<View, Long> {

    @Query("select count (v.id) from View v where v.trainerId = :trainerId")
    Long findSumView(@Param("trainerId") Long trainerId);;
}
