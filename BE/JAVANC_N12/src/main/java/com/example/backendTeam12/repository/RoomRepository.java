package com.example.backendTeam12.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backendTeam12.model.Room;

public interface  RoomRepository extends JpaRepository<Room, Long>{
    public List<Room> findByBookingBookingId(Long bookingId);
    public List<Room> findByHomestayHomestayId(Long homestayId);

    @Query("SELECT DISTINCT r.roomType FROM Room r WHERE r.homestay.id = :homestayId")
    List<Integer> findRoomTypesByHomestayId(@Param("homestayId") Long homestayId);

}
