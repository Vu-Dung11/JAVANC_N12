package com.example.backendTeam12.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backendTeam12.model.Homestay;
import com.example.backendTeam12.repository.HomestayRepository;
import com.example.backendTeam12.service.HomestayService;

@Service
public class HomestayServiceImpl implements HomestayService {

    @Autowired
    private HomestayRepository homestayRepository;

    @Override
    public Homestay createHomestay(Homestay homestay) {
        return homestayRepository.save(homestay);
    }

    @Override
    public Homestay updateHomestay(Long id, Homestay homestay) {
        Homestay existingHomestay = homestayRepository.findById(id).orElseThrow(() -> new RuntimeException("Homestay not found"));
        
        //update
        existingHomestay.setName(homestay.getName());
        existingHomestay.setDescription(homestay.getDescription());
        existingHomestay.setWard(homestay.getWard());
        existingHomestay.setDistrict(homestay.getDistrict());
        existingHomestay.setProvince(homestay.getProvince());
        //save
        return homestayRepository.save(existingHomestay);
    }

    @Override
    public void deleteHomestay(Long id) {
        homestayRepository.deleteById(id);
    }

    @Override
    public Optional<Homestay> getHomestayById(Long id) {
        return homestayRepository.findById(id);
    }

    @Override
    public List<Homestay> getAllHomestays() {
        return homestayRepository.findAll();
    }

    @Override
    public List<Homestay> getHomestaysByOwner(Long userId) {
        return homestayRepository.findByOwnerUserId(userId);
    }

    @Override
    public List<Homestay> searchHomestays(String keyword) {
        return homestayRepository.findByNameContainingOrDescriptionContaining(keyword, keyword);
    }

    @Override
    public List<Homestay> getHomestaysByLocation(String province, String district, String ward) {
        return homestayRepository.findByProvinceAndDistrictAndWard(province, district, ward);
    }
} 