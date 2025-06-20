package com.example.backendTeam12.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backendTeam12.model.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long>{
    
}
