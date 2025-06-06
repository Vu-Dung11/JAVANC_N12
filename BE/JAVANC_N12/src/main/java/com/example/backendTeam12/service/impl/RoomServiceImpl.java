package com.example.backendTeam12.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backendTeam12.model.Homestay;
import com.example.backendTeam12.model.Room;
import com.example.backendTeam12.repository.HomestayRepository;
import com.example.backendTeam12.repository.RoomRepository;
import com.example.backendTeam12.service.RoomService;


@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HomestayRepository homestayRepository;

    @Override
    public Room createRoom(Room room) {
        room.setStatus(0);
        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(Long id, Room room) {
        Room existingRoom = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));

        existingRoom.setRoomName(room.getRoomName());
        existingRoom.setRoomType(room.getRoomType());
        existingRoom.setPrice(room.getPrice());
        existingRoom.setStatus(room.getStatus());

        //sửa id homestay
        if (room.getHomestay() != null && room.getHomestay().getHomestayId() != null ){
            Long homestayId = room.getHomestay().getHomestayId();
            Homestay homestay = homestayRepository.findById(homestayId)
                                .orElseThrow(() -> new RuntimeException("homestay with id" + homestayId + "not found"));
            existingRoom.setHomestay(homestay);
            }

        return roomRepository.save(existingRoom);
    }

    @Override
    public Homestay getHomestayByRoomId(Long roomId){

        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
            Long homestayId = room.getHomestay().getHomestayId();
            Homestay homestay = homestayRepository.findById(homestayId).orElseThrow(() -> new RuntimeException("homestay with " + homestayId +" not found"));
        return homestay;
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> getRoomsByBookingBookingId(Long bookingId) {
      return roomRepository.findByBookingBookingId(bookingId);
    }

    @Override
    public List<Room> getRoomsByHomestayHomestayId(Long homestayId) {
        return roomRepository.findByHomestayHomestayId(homestayId);
    }
    
}
