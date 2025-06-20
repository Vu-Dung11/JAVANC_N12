package com.example.backendTeam12.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backendTeam12.model.BookingHistory;
import com.example.backendTeam12.repository.BookingHistoryRepository;
import com.example.backendTeam12.service.BookingHistoryService;

@Service
public class BookingHistoryServiceImpl implements  BookingHistoryService {
    @Autowired
    private BookingHistoryRepository bookingHistoryRepository;

    @Override
    public List<BookingHistory> getHistoryByUserId(Long userId) {
        return bookingHistoryRepository.findByUserId(userId);
    }
}
