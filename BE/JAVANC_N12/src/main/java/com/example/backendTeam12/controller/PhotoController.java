package com.example.backendTeam12.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backendTeam12.model.Photo;
import com.example.backendTeam12.service.PhotoService;


@RestController
@RequestMapping("/api/photos")
@CrossOrigin(origins = "*")

public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping
    public ResponseEntity<Photo> createPhoto(@RequestBody Photo photo) {
        return ResponseEntity.ok(photoService.createPhoto(photo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Photo> updatePhoto(@PathVariable Long id, @RequestBody Photo photo) {
        Photo updatedPhoto = photoService.updatePhoto(id, photo);
        if (updatedPhoto != null) {
            return ResponseEntity.ok(updatedPhoto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long id) {
        photoService.deletePhoto(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Photo>> getAllPhotos() {
        return ResponseEntity.ok(photoService.getAllPhotos());
    }


} 
