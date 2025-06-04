package com.example.backendTeam12.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backendTeam12.model.Photo;
import com.example.backendTeam12.repository.PhotoRepository;
import com.example.backendTeam12.service.PhotoService;

@Service
public class PhotoServiceImpl implements PhotoService{

    @Autowired
    private PhotoRepository photoRepository;

    @Override
    public Photo createPhoto(Photo photo) {
        return photoRepository.save(photo);
    }

    @Override
    public Photo updatePhoto(Long id, Photo photo) {
       return photoRepository.save(photo);
    }

    @Override
    public void deletePhoto(Long id) {
       photoRepository.deleteById(id);
    }

    @Override
    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }
    
}
