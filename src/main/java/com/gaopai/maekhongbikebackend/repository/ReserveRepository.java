package com.gaopai.maekhongbikebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaopai.maekhongbikebackend.domain.Reserve;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {

    @Query("select r from Reserve r where r.user.username =:username")
    List<Reserve> findByCreateBy(@Param("username") String username);

}
