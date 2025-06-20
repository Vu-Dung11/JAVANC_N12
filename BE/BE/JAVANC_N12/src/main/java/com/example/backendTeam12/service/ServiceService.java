package com.example.backendTeam12.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.example.backendTeam12.model.HomestayService;

public interface ServiceService {
    HomestayService createService(HomestayService service);
    HomestayService updateService(Long id, HomestayService service);
    void deleteService(Long id);
    Optional<HomestayService> getServiceById(Long id);
    List<HomestayService> getAllServices();
    List<HomestayService> getServiceByKeyWord(String keyWord);
    List<HomestayService> getServiceByPriceRange(BigDecimal price1, BigDecimal price2);
}
