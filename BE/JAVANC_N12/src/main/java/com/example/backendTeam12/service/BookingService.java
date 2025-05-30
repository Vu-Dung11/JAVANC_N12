package com.example.backendTeam12.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.backendTeam12.model.Booking;

public interface BookingService {
    Booking createBooking(Booking booking);
    Booking updateBooking(Long id, Booking booking);
    void deleteBooking(Long id);
    Optional<Booking> getBookingById(Long id);
    List<Booking> getAllBookings();
    List<Booking> getBookingsByUserId(Long userId);
    List<Booking> getBookingsByStatus(Integer status);
    List<Booking> getBookingsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    Booking confirmBooking(Long id);
    Booking cancelBooking(Long id, String reason);
    Booking completeBooking(Long id);
} 