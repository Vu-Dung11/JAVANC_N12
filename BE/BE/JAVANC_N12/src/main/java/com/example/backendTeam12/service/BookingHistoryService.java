package com.example.backendTeam12.service;

import java.util.List;

import com.example.backendTeam12.model.BookingHistory;

public interface  BookingHistoryService {
    List<BookingHistory> getHistoryByUserId(Long userId);
}
