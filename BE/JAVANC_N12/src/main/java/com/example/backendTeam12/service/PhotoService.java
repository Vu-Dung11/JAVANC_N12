package com.example.backendTeam12.service;

import java.util.List;


import com.example.backendTeam12.model.Photo;


public interface PhotoService {
    Photo createPhoto(Photo photo);
    Photo updatePhoto(Long id, Photo photo);
    void deletePhoto(Long id);
    List<Photo> getAllPhotos();
}
