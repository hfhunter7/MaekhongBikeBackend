package com.gaopai.maekhongbikebackend.repository.impl;

import com.gaopai.maekhongbikebackend.domain.TokenUser;
import com.gaopai.maekhongbikebackend.repository.TokenUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenUserRepositoryService {

    @Autowired
    private TokenUserRepository tokenUserRepository;

    public TokenUserRepositoryService() {

    }

    public TokenUser findTokenByTokenString(String token) {
        return tokenUserRepository.findTokenByTokenString(token);
    }

    public TokenUser getTokenByUsername(String username) {
        List<TokenUser> tokens = tokenUserRepository.findTokenByUsername(username);
        if(tokens.size() > 0){
            return tokens.get(0);
        }
        return null;
    }

    public TokenUser save(TokenUser tokenUser) {
        return tokenUserRepository.save(tokenUser);
    }

    public void update(TokenUser tokenUser) {
        tokenUserRepository.save(tokenUser);
    }

    public void update(List<TokenUser> tokenUserList) {
        tokenUserRepository.save(tokenUserList);
    }

    public void delete(Long id) {
        tokenUserRepository.delete(id);
    }

    public void delete(TokenUser tokenUser) {
        tokenUserRepository.delete(tokenUser);
    }

    public TokenUser find(Long id) {
        return tokenUserRepository.findOne(id);
    }

    public List<TokenUser> findAll() {
        return tokenUserRepository.findAll();
    }
}
