package com.gaopai.maekhongbikebackend.repository.impl;

import com.gaopai.maekhongbikebackend.domain.View;
import com.gaopai.maekhongbikebackend.repository.ViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewRepositoryService {


    @Autowired
    private ViewRepository viewRepository;

    public ViewRepositoryService(){

    }

    public View save(View view) {
        return viewRepository.save(view);
    }

    public void update(View view) {
        viewRepository.save(view);
    }

    public void update(List<View> viewList) {
        viewRepository.save(viewList);
    }

    public void delete(Long id) {
        viewRepository.delete(id);
    }

    public void delete(View view) {
        viewRepository.delete(view);
    }

    public View find(Long id) {
        return viewRepository.findOne(id);
    }

    public List<View> findAll() {
        return viewRepository.findAll();
    }

    public Long findSumView(Long trainerId){
        return viewRepository.findSumView(trainerId);
    }

}
