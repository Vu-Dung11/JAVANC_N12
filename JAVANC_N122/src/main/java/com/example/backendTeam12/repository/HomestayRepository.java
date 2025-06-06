package com.example.backendTeam12.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backendTeam12.model.Homestay;

public interface  HomestayRepository extends JpaRepository<Homestay, Long>{

    public List<Homestay> findByOwnerUserId(Long userId);

    public List<Homestay> findByNameContainingOrDescriptionContaining(String keyword, String keyword0);

    public List<Homestay> findByProvinceAndDistrictAndWard(String province, String district, String ward);
    
}
