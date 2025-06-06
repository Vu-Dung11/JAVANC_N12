package com.example.backendTeam12.controller;

import com.example.backendTeam12.model.Homestay;
import com.example.backendTeam12.service.HomestayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/homestays")
@CrossOrigin(origins = "*")
public class HomestayController {

    @Autowired
    private HomestayService homestayService;

    @PostMapping
    public ResponseEntity<Homestay> createHomestay(@RequestBody Homestay homestay) {
        return ResponseEntity.ok(homestayService.createHomestay(homestay));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Homestay> updateHomestay(@PathVariable Long id, @RequestBody Homestay homestay) {
        Homestay updatedHomestay = homestayService.updateHomestay(id, homestay);
        if (updatedHomestay != null) {
            return ResponseEntity.ok(updatedHomestay);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHomestay(@PathVariable Long id) {
        homestayService.deleteHomestay(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Homestay> getHomestayById(@PathVariable Long id) {
        return homestayService.getHomestayById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Homestay>> getAllHomestays() {
        return ResponseEntity.ok(homestayService.getAllHomestays());
    }

    @GetMapping("/owner/{userId}")
    public ResponseEntity<List<Homestay>> getHomestaysByOwner(@PathVariable Long userId) {
        return ResponseEntity.ok(homestayService.getHomestaysByOwner(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Homestay>> searchHomestays(@RequestParam String keyword) {
        return ResponseEntity.ok(homestayService.searchHomestays(keyword));
    }

    @GetMapping("/location")
    public ResponseEntity<List<Homestay>> getHomestaysByLocation(
            @RequestParam(required = false) String province,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String ward) {
        return ResponseEntity.ok(homestayService.getHomestaysByLocation(province, district, ward));
    }
} 