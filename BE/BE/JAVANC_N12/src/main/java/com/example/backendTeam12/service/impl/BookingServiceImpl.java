package com.example.backendTeam12.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backendTeam12.model.Booking;
import com.example.backendTeam12.model.BookingHistory;
import com.example.backendTeam12.model.Homestay;
import com.example.backendTeam12.model.Room;
import com.example.backendTeam12.model.User;
import com.example.backendTeam12.repository.BookingHistoryRepository;
import com.example.backendTeam12.repository.BookingRepository;
import com.example.backendTeam12.repository.RoomRepository;
import com.example.backendTeam12.repository.UserRepository;
import com.example.backendTeam12.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingHistoryRepository bookingHistoryRepository;

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
    public boolean deleteBooking(Long bookingId) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isEmpty()) {
            return false;
        }

        Booking booking = optionalBooking.get();


        List<Room> rooms = roomRepository.findByBooking(booking);

        for (Room room : rooms) {
            room.setBooking(null);
            room.setStatus(1);
        }
        roomRepository.saveAll(rooms);

        booking.setStatus(2);
        bookingRepository.save(booking);

        return true;
    }

     @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public Booking createBooking(Booking booking) {
        // Kiểm tra và gán User
        if (booking.getUser() != null && booking.getUser().getUserId() != null) {
            User user = userRepository.findById(booking.getUser().getUserId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
            booking.setUser(user);
        }

        // Lưu booking trước để có bookingId
        Booking savedBooking = bookingRepository.save(booking);

        // Gán bookingId vào các Room đã tồn tại
        if (booking.getRooms() != null && !booking.getRooms().isEmpty()) {
            for (Room room : booking.getRooms()) {
                Room existingRoom = roomRepository.findById(room.getRoomId())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng với ID: " + room.getRoomId()));

                existingRoom.setBooking(savedBooking);
                roomRepository.save(existingRoom);

                //lưu vào bảng bookinghistory
                Homestay homestay = existingRoom.getHomestay();
                String address = homestay.getProvince() +  ", " + homestay.getDistrict() + ", " + homestay.getWard();

                BookingHistory bookingHistory = new BookingHistory();

                bookingHistory.setAddress(address);
                bookingHistory.setUserId(savedBooking.getUser().getUserId());
                bookingHistory.setRoomName(existingRoom.getRoomName());
                bookingHistory.setCheckInDate(savedBooking.getCheckInDate());
                bookingHistory.setTotalPrice(savedBooking.getTotalPrice());
                bookingHistory.setDepositPrice(savedBooking.getDepositPrice());
                bookingHistory.setCreatedAt(LocalDateTime.now());

                bookingHistoryRepository.save(bookingHistory);
            }
        }

        return savedBooking;
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

    @Override
    public int percentUserPotential(){
        long totalUsersWithBooking = bookingRepository.countDistinctUsersWithBooking();
        if (totalUsersWithBooking == 0) return 0;

        int repeatUsers = bookingRepository.findUserIdsWithMoreThanOneBooking().size();
        return (int) (((double) repeatUsers / totalUsersWithBooking) * 100);
    }
}