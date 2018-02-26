package com.gaopai.maekhongbikebackend.repository.impl;

import com.gaopai.maekhongbikebackend.domain.Reserve;
import com.gaopai.maekhongbikebackend.domain.Users;
import com.gaopai.maekhongbikebackend.repository.ReserveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReserveRepositoryService {

    @Autowired
    private ReserveRepository reserveRepository;

    public ReserveRepositoryService() {

    }

    public Reserve save(Reserve reserve) {
        return reserveRepository.save(reserve);
    }

    public void update(Reserve reserve) {
        reserveRepository.save(reserve);
    }

    public void update(List<Reserve> reserveList) {
        reserveRepository.save(reserveList);
    }

    public void delete(Long id) {
        reserveRepository.delete(id);
    }

    public void delete(Reserve reserve) {
        reserveRepository.delete(reserve);
    }

    public Reserve find(Long id) {
        return reserveRepository.findOne(id);
    }

    public List<Reserve> findAll() {
        return reserveRepository.findAll();
    }

    public List<Reserve> findByCreateBy(String username){
        return reserveRepository.findByCreateBy(username);
    }
}
