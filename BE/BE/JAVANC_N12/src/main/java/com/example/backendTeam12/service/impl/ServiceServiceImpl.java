package com.example.backendTeam12.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backendTeam12.model.HomestayService;
import com.example.backendTeam12.repository.ServiceRepository;
import com.example.backendTeam12.service.ServiceService;

@Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public HomestayService createService(HomestayService service) {
        return serviceRepository.save(service);
    }

    @Override
    public HomestayService updateService(Long id, HomestayService service) {
        HomestayService existingService = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        existingService.setServiceName(service.getServiceName());
        existingService.setDescription(service.getDescription());
        existingService.setPrice(service.getPrice());

        return serviceRepository.save(existingService);
    }

    @Override
    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }

    @Override
    public Optional<HomestayService> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    @Override
    public List<HomestayService> getAllServices() {
        return serviceRepository.findAll();
    }

    @Override
    public List<HomestayService> getServiceByKeyWord(String keyWord) {
        return serviceRepository.findByServiceNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyWord, keyWord);
    }

    @Override
    public List<HomestayService> getServiceByPriceRange(BigDecimal price1, BigDecimal price2) {
        return serviceRepository.findByPriceBetween(price1, price2);
    }
}
