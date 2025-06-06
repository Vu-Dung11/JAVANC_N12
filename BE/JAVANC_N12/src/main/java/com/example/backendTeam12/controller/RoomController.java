package com.example.backendTeam12.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backendTeam12.model.Homestay;
import com.example.backendTeam12.model.Room;
import com.example.backendTeam12.service.RoomService;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "*")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        return ResponseEntity.ok(roomService.createRoom(room));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room room) {
        Room updatedRoom = roomService.updateRoom(id, room);
        if (updatedRoom != null) {
            return ResponseEntity.ok(updatedRoom);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/homestay/{homestayId}")
    public ResponseEntity<List<Room>> getRoomsByHomestayHomestayId(@PathVariable Long homestayId) {
        return ResponseEntity.ok(roomService.getRoomsByHomestayHomestayId(homestayId));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<Room>> getRoomsByBookingBookingId(@PathVariable Long bookingId) {
        return ResponseEntity.ok(roomService.getRoomsByBookingBookingId(bookingId));
    }

    @GetMapping("/gethomestay/{roomId}")
    public ResponseEntity<Homestay> getHomestayByRoomId(@PathVariable Long roomId) {
        return ResponseEntity.ok(roomService.getHomestayByRoomId(roomId));
    }
}