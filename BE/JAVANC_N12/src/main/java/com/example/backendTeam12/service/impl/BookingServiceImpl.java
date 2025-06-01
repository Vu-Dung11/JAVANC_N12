package com.example.backendTeam12.service.impl;

import com.example.backendTeam12.model.Booking;
import com.example.backendTeam12.model.User;
import com.example.backendTeam12.repository.BookingRepository;
import com.example.backendTeam12.repository.UserRepository;
import com.example.backendTeam12.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Booking updateBooking(Long bookingId, Booking booking) {
         return bookingRepository.findById(bookingId).map(existingBooking -> {
        existingBooking.setCheckInDate(booking.getCheckInDate());
        existingBooking.setCheckOutDate(booking.getCheckOutDate());
        existingBooking.setDepositPrice(booking.getDepositPrice());
        existingBooking.setTotalPrice(booking.getTotalPrice());
        existingBooking.setStatus(booking.getStatus());
        if (booking.getUser() != null && booking.getUser().getUserId() != null) {
            User user = userRepository.findById(booking.getUser().getUserId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
            existingBooking.setUser(user);
        }
        return bookingRepository.save(existingBooking);
        }).orElse(null);
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

     @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }


    @Override
    public Booking createBooking(Booking booking) {
        // Kiểm tra user tồn tại
        if (booking.getUser() != null && booking.getUser().getUserId() != null) {
            User user = userRepository.findById(booking.getUser().getUserId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
            booking.setUser(user);
        }
        // Gán booking cho rooms (nếu có)
        if (booking.getRooms() != null) {
            booking.getRooms().forEach(room -> room.setBooking(booking));
        }
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> findBookingsByUserUserId(Long userId) {
        return bookingRepository.findByUserUserId(userId);
    }

    @Override
    public List<Booking> findBookingsByStatus(Integer status) {
        return bookingRepository.findByStatus(status);
    }

    @Override
    public List<Booking> findBookingsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return bookingRepository.findByDateRange(startDate, endDate);
    }
}