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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backendTeam12.dto.HomestaySummaryDTO;
import com.example.backendTeam12.model.Homestay;
import com.example.backendTeam12.service.HomestayService;

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

    @GetMapping("/search/keyword")
    public ResponseEntity<List<Homestay>> getHomestaysByKeyword(@RequestParam("keyword") String keyword) {
        List<Homestay> result = homestayService.getHomestaysByKeyword(keyword);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/summary")
    public List<HomestaySummaryDTO> getHomestaysBySummary(){
        return homestayService.getHomestaysBySummary();
    }

    @GetMapping("/summary/search/keyword")
    public ResponseEntity<List<HomestaySummaryDTO>> searchByAddress(@RequestParam String keyword) {
        List<HomestaySummaryDTO> result = homestayService.searchHomestaysByApproximateAddress(keyword);
        return ResponseEntity.ok(result);
    }

} 