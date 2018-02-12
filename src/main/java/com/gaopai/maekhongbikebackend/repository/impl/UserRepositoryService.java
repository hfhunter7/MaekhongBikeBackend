package com.gaopai.maekhongbikebackend.repository.impl;

import com.gaopai.maekhongbikebackend.domain.Users;
import com.gaopai.maekhongbikebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRepositoryService {

    @Autowired
    private UserRepository userRepository;

    public UserRepositoryService() {

    }

    public Users save(Users users) {
        return userRepository.save(users);
    }

    public void update(Users users) {
        userRepository.save(users);
    }

    public void update(List<Users> List) {
        userRepository.save(List);
    }

    public void delete(Long id) {
        userRepository.delete(id);
    }

    public void delete(Users users) {
        userRepository.delete(users);
    }

    public Users find(Long id) {
        return userRepository.findOne(id);
    }

    public List<Users> findAll() {
        return userRepository.findAll();
    }

    public Users findByToken(String token) {
        return userRepository.findByToken(token);
    }

    public Users findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
