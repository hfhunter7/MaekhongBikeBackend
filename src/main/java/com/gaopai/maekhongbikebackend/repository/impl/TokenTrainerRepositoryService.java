package com.gaopai.maekhongbikebackend.repository.impl;

import com.gaopai.maekhongbikebackend.domain.Trainer;
import com.gaopai.maekhongbikebackend.domain.TokenTrainer;
import com.gaopai.maekhongbikebackend.repository.TokenTrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenTrainerRepositoryService {

	@Autowired
	private TokenTrainerRepository tokenRepository;

	@Autowired
    CounterService counterService;

	@Autowired
    GaugeService gaugeService;

	public TokenTrainerRepositoryService() {
	}

	public TokenTrainer getTokenByUserId(Long userId) {
		List<TokenTrainer> tokens = tokenRepository.findTokenByUserId(userId);
		if(tokens.size() > 0){
			return tokens.get(0);
		}
		return null;
	}

	public TokenTrainer save(TokenTrainer newToken) {
		return tokenRepository.save(newToken);
	}
	
	public void update(TokenTrainer newToken) {
		tokenRepository.save(newToken);
	}

	public TokenTrainer findTokenByTokenString(String token) {
		return tokenRepository.findTokenByTokenString(token);
	}
	
	public TokenTrainer findTokenByTokenStringNotExpire(String token) {
		return tokenRepository.findTokenByTokenStringNotExpire(token);
	}

	public void delete(Trainer user) {
		TokenTrainer token = tokenRepository.findTokenByUser(user);
		tokenRepository.delete(token);
	}

}
