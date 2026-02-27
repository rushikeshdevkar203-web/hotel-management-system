package com.hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.hotel.entity.Room;
import com.hotel.repository.RoomRepository;

@Controller
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    // Show All Rooms
    @GetMapping("/rooms")
    public String viewRooms(Model model) {
        model.addAttribute("rooms", roomRepository.findAll());
        return "rooms";
    }

    // Show Add Room Form
    @GetMapping("/add-room")
    public String showAddRoomForm(Model model) {
        model.addAttribute("room", new Room());
        return "add-room";
    }

    // Save Room
    @PostMapping("/save-room")
    public String saveRoom(@ModelAttribute Room room) {
        roomRepository.save(room);
        return "redirect:/rooms";
    }
    
 // Show Update Form
    @GetMapping("/edit-room/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Room Id: " + id));

        model.addAttribute("room", room);
        return "edit-room";
    }

    // Update Room
    @PostMapping("/update-room/{id}")
    public String updateRoom(@PathVariable Long id, @ModelAttribute Room room) {

        Room existingRoom = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Room Id: " + id));

        existingRoom.setRoomNo(room.getRoomNo());
        existingRoom.setRoomType(room.getRoomType());
        existingRoom.setPrice(room.getPrice());
        existingRoom.setStatus(room.getStatus());

        roomRepository.save(existingRoom);

        return "redirect:/rooms";
    }
}