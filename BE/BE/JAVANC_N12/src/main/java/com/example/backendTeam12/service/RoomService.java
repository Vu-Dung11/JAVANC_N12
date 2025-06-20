package com.example.backendTeam12.service;

import java.util.List;
import java.util.Optional;

import com.example.backendTeam12.model.Homestay;
import com.example.backendTeam12.model.Room;

public interface RoomService {
    Room createRoom(Room room);
    Room updateRoom(Long id, Room room);
    void deleteRoom(Long id);
    Optional <Room> getRoomById(Long id);
    List<Room> getAllRooms();
    List<Room> getRoomsByBookingBookingId(Long bookingId);
    List<Room> getRoomsByHomestayHomestayId(Long bookingId);
    Homestay getHomestayByRoomId(Long roomId);
    Room updateRoomByBookingId(Long bookingId, Long roomId);
    List<Room> getRoomsByKeyword(String keyword);

    int percentAvailableRoom();// phòng chưa sử dụng
    int percentOccupiedRoom();//phòng đang sử dụng
}
