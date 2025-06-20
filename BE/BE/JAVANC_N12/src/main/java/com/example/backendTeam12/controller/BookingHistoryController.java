package com.example.backendTeam12.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backendTeam12.model.BookingHistory;
import com.example.backendTeam12.service.BookingHistoryService;


@RestController
@RequestMapping("/api/booking-history")
@CrossOrigin(origins = "*")
public class BookingHistoryController {
    @Autowired
    private BookingHistoryService bookingHistoryService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingHistory>> getHistoryByUser(@PathVariable Long userId) {
        List<BookingHistory> historyList = bookingHistoryService.getHistoryByUserId(userId);
        if (historyList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(historyList);
    }
}
