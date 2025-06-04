package com.example.backendTeam12.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backendTeam12.model.Homestay;
import com.example.backendTeam12.model.User;
import com.example.backendTeam12.repository.HomestayRepository;
import com.example.backendTeam12.repository.UserRepository;
import com.example.backendTeam12.service.HomestayService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Service
public class HomestayServiceImpl implements HomestayService {

    @Autowired
    private HomestayRepository homestayRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Homestay createHomestay(Homestay homestay) {
        if (homestay == null) {
            throw new RuntimeException("Homestay không để trống");
        }

        Long userId = 46L;
        User owner = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Mặc định"));

        homestay.setOwner(owner);
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
        
        if (homestay.getOwner() != null && homestay.getOwner().getUserId() != null) {
            Long userId = homestay.getOwner().getUserId();
            User owner = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User with ID " + userId + " not found"));
            existingHomestay.setOwner(owner);
        }
        
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

    @Override
    public List<Homestay> getHomestaysByKeyword(String keyword) {
        if (keyword == null || keyword.isEmpty()){
            return homestayRepository.findAll();
        }
        String lowerKeyword = "%" + keyword.toLowerCase() + "%";
        String jpql = "SELECT h FROM Homestay h WHERE " +
                      "LOWER(h.name) LIKE :kw OR " +
                      "LOWER(h.description) LIKE :kw OR " +
                      "LOWER(h.ward) LIKE :kw OR " +
                      "LOWER(h.district) LIKE :kw OR " +
                      "LOWER(h.province) LIKE :kw";
        TypedQuery<Homestay> query = entityManager.createQuery(jpql, Homestay.class);
        query.setParameter("kw", lowerKeyword);
        return query.getResultList();
    }
} 