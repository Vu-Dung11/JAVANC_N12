package com.example.backendTeam12.service;

import com.example.backendTeam12.model.Homestay;
import java.util.List;
import java.util.Optional;

public interface HomestayService {
    Homestay createHomestay(Homestay homestay);
    Homestay updateHomestay(Long id, Homestay homestay);
    void deleteHomestay(Long id);
    Optional<Homestay> getHomestayById(Long id);
    List<Homestay> getAllHomestays();
    List<Homestay> getHomestaysByOwner(Long userId);
    List<Homestay> searchHomestays(String keyword);
    List<Homestay> getHomestaysByLocation(String province, String district, String ward);
} 