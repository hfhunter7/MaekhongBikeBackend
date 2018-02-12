package com.gaopai.maekhongbikebackend.repository;

import com.gaopai.maekhongbikebackend.domain.Trainer;
import com.gaopai.maekhongbikebackend.domain.TokenTrainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TokenTrainerRepository extends JpaRepository<TokenTrainer, Long> {

	TokenTrainer findTokenById(Long tokenId);
    
    @Query("select t from TokenTrainer t where t.trainer.id = :userId")
    List<TokenTrainer> findTokenByUserId(@Param("userId") Long userId);
    
    @Query("select t from TokenTrainer t where t.tokenKey = :token and t.expireDate > CURRENT_TIMESTAMP")
    TokenTrainer findTokenByTokenString(@Param("token") String token);
    
    @Query("select t from TokenTrainer t where t.tokenKey = :token")
    TokenTrainer findTokenByTokenStringNotExpire(@Param("token") String token);

    @Query("select t from TokenTrainer t where t.trainer = :trainer")
    TokenTrainer findTokenByUser(@Param("trainer") Trainer trainer);
	
}
