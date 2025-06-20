package com.example.backendTeam12.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backendTeam12.model.Booking;
import com.example.backendTeam12.model.Homestay;
import com.example.backendTeam12.model.Room;
import com.example.backendTeam12.repository.BookingRepository;
import com.example.backendTeam12.repository.HomestayRepository;
import com.example.backendTeam12.repository.RoomRepository;
import com.example.backendTeam12.service.RoomService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HomestayRepository homestayRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @PersistenceContext
    private EntityManager entityManager;

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
    public Room updateRoomByBookingId(Long bookingId, Long roomId){
        Room updateRoom = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("booking not exist"));

        updateRoom.setBooking(booking);

        return roomRepository.save(updateRoom);
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

    @Override
    public List<Room> getRoomsByKeyword(String keyword) {
        if (keyword == null || keyword.isEmpty()){
            return roomRepository.findAll();
        }
        String lowerKeyword = "%" + keyword.toLowerCase() + "%";
        String jpql = "SELECT h FROM Room h WHERE " +
                      "LOWER(h.roomName) LIKE :kw";
        TypedQuery<Room> query = entityManager.createQuery(jpql, Room.class);
        query.setParameter("kw", lowerKeyword);
        return query.getResultList();
    }
    
    @Override
    public int percentAvailableRoom(){
        long totalRoom = roomRepository.countTotalRooms();
        if (totalRoom == 0) return 0;

        long available = roomRepository.countAvailableRooms();
        return (int) (((double) available / totalRoom) * 100);
    }

    @Override
    public int percentOccupiedRoom() {
        long totalRoom = roomRepository.countTotalRooms();
        if (totalRoom == 0) return 0;

         long occupiedRoom = roomRepository.countOccupiedRooms();
        return (int) (((double) occupiedRoom / totalRoom) * 100);
    }
}
