package com.example.backendTeam12.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backendTeam12.model.Booking;
import com.example.backendTeam12.repository.BookingRepository;
import com.example.backendTeam12.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Booking createBooking(Booking booking) {
        booking.setStatus(0); // 0: Pending
        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateBooking(Long id, Booking booking) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Update booking details
        existingBooking.setCheckInDate(booking.getCheckInDate());
        existingBooking.setCheckOutDate(booking.getCheckOutDate());
        existingBooking.setDepositPrice(booking.getDepositPrice());
        existingBooking.setTotalPrice(booking.getTotalPrice());
        existingBooking.setStatus(booking.getStatus());
        existingBooking.setUpdatedAt(LocalDateTime.now());

        return bookingRepository.save(existingBooking);
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUser_UserId(userId);
    }

    @Override
    public List<Booking> getBookingsByStatus(Integer status) {
        return bookingRepository.findByStatus(status);
    }

    @Override
    public List<Booking> getBookingsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return bookingRepository.findByCheckInDateBetweenOrCheckOutDateBetween(
            startDate, endDate, startDate, endDate);
    }

    @Override
    public Booking confirmBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(1); // 1: Confirmed
        booking.setUpdatedAt(LocalDateTime.now());
        return bookingRepository.save(booking);
    }

    @Override
    public Booking cancelBooking(Long id, String reason) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(2); // 2: Cancelled
        booking.setUpdatedAt(LocalDateTime.now());
        // TODO: Add cancellation reason field to Booking model
        return bookingRepository.save(booking);
    }

    @Override
    public Booking completeBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(3); // 3: Completed
        booking.setUpdatedAt(LocalDateTime.now());
        return bookingRepository.save(booking);
    }
} 