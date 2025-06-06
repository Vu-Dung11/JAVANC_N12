package com.example.backendTeam12.service;

import com.example.backendTeam12.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    Booking updateBooking(Long bookingId, Booking booking);
    Booking createBooking(Booking booking);
    void deleteBooking(Long id);
    List<Booking> getAllBookings();
    List<Booking> findBookingsByUserUserId(Long userId);
    List<Booking> findBookingsByStatus(Integer status);
    List<Booking> findBookingsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}