package com.gaopai.maekhongbikebackend.repository;

import com.gaopai.maekhongbikebackend.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<Users, Long> {
    @Query("select u from Users u where u.token.tokenKey = :token and u.token.expireDate > CURRENT_TIMESTAMP")
    Users findByToken(@Param("token") String token);

    Users findByUsernameAndPassword(String username, String password);
}
