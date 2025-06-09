package com.example.backendTeam12.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.backendTeam12.model.Booking;

public interface BookingService {
    Booking updateBooking(Long bookingId, Booking booking);
    Booking createBooking(Booking booking);
    boolean deleteBooking(Long bookingId);
    List<Booking> getAllBookings();
    List<Booking> findBookingsByUserUserId(Long userId);
    List<Booking> findBookingsByStatus(Integer status);
    List<Booking> findBookingsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    Optional<Booking> getBookingById(Long id);

    int percentUserPotential();
}