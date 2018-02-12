package com.gaopai.maekhongbikebackend.repository.impl;

import com.gaopai.maekhongbikebackend.domain.Trainer;
import com.gaopai.maekhongbikebackend.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerRepositoryService {

    @Autowired
    private TrainerRepository trainerRepository;

    public TrainerRepositoryService() {

    }

    public Trainer save(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    public void update(Trainer trainer) {
        trainerRepository.save(trainer);
    }

    public void updateAddress(Trainer trainer) {
        trainerRepository.save(trainer);
    }

    public void update(List<Trainer> trainerList) {
        trainerRepository.save(trainerList);
    }

    public void delete(Long id) {
        trainerRepository.delete(id);
    }

    public void delete(Trainer trainer) {
        trainerRepository.delete(trainer);
    }

    public Trainer find(Long id) {
        return trainerRepository.findOne(id);
    }

    public List<Trainer> findAll() {
        return trainerRepository.findAll();
    }

    public Trainer findByEmail(String email) {
        return trainerRepository.findByEmail(email);
    }

    public Trainer findByGoogleId(String googleId) {
        return trainerRepository.findByGoogleId(googleId);
    }

    public Trainer findByEmailAndGoogleId(String email, String googleId) {
        return trainerRepository.findByEmailAndGoogleId(email, googleId);
    }

    public Trainer findByToken(String token) {
        return trainerRepository.findByToken(token);
    }
}
