package com.example.backendTeam12.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backendTeam12.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
       List<Booking> findByUserUserId(Long userId);

       // Tìm booking theo status
       List<Booking> findByStatus(Integer status);

       // Tìm booking giữa hai khoảng thời gian
       @Query("SELECT b FROM Booking b WHERE " +
              "(b.checkInDate BETWEEN :startDate AND :endDate) OR " +
              "(b.checkOutDate BETWEEN :startDate AND :endDate) OR " +
              "(b.checkInDate <= :startDate AND b.checkOutDate >= :endDate)")
       List<Booking> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                                   @Param("endDate") LocalDateTime endDate);

       @Query("select b.user.userId from Booking b group by b.user.userId having count(b) >= 2")
       List<Long> findUserIdsWithMoreThanOneBooking();

       @Query("select count(distinct b.user.userId) from Booking b")
       long countDistinctUsersWithBooking();
}