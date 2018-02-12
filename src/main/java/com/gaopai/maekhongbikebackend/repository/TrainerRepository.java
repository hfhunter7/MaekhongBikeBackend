package com.gaopai.maekhongbikebackend.repository;

import com.gaopai.maekhongbikebackend.domain.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

	Trainer findByEmail(String email);

	Trainer findByGoogleId(String googleId);

	Trainer findByEmailAndGoogleId(String email, String googleId);

	@Query("select t from Trainer t where t.token.tokenKey = :token and t.token.expireDate > CURRENT_TIMESTAMP")
	Trainer findByToken(@Param("token") String token);
}
