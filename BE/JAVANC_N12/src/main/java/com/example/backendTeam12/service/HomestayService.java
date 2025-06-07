package com.example.backendTeam12.service;

import java.util.List;
import java.util.Optional;

import com.example.backendTeam12.dto.HomestaySummaryDTO;
import com.example.backendTeam12.model.Homestay;

public interface HomestayService {
    Homestay createHomestay(Homestay homestay);
    Homestay updateHomestay(Long id, Homestay homestay);
    void deleteHomestay(Long id);
    Optional<Homestay> getHomestayById(Long id);
    List<Homestay> getAllHomestays();
    List<Homestay> getHomestaysByOwner(Long userId);
    List<Homestay> searchHomestays(String keyword);
    List<Homestay> getHomestaysByLocation(String province, String district, String ward);
    List<Homestay> getHomestaysByKeyword(String keyword);
    
    List<HomestaySummaryDTO> getHomestaysBySummary();
    List<HomestaySummaryDTO> searchHomestaysByApproximateAddress(String keyword);

}