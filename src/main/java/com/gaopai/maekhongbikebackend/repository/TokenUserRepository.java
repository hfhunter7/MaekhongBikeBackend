package com.gaopai.maekhongbikebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaopai.maekhongbikebackend.domain.TokenUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TokenUserRepository extends JpaRepository<TokenUser, Long> {
    @Query("select t from TokenUser t where t.users.username = :username")
    List<TokenUser> findTokenByUsername(@Param("username") String username);

    @Query("select t from TokenUser t where t.tokenKey = :token and t.expireDate > CURRENT_TIMESTAMP")
    TokenUser findTokenByTokenString(@Param("token")String token);
}
