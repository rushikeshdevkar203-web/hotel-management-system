package com.hotel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.hotel.entity.Booking;
import com.hotel.entity.Room;
import com.hotel.repository.BookingRepository;
import com.hotel.repository.RoomRepository;

@Controller
public class BookingController {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public BookingController(BookingRepository bookingRepository,
                             RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    // Show Book Room Form
    @GetMapping("/book-room")
    public String showBookingForm(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("rooms", roomRepository.findAll());
        return "booking";
    }

    // Save Booking
    @PostMapping("/save-booking")
    public String saveBooking(@ModelAttribute Booking booking) {

        // Save booking first
        booking.setStatus("CONFIRMED");
        bookingRepository.save(booking);

        // Find room by roomNo
        Room room = roomRepository.findByRoomNo(booking.getRoomNo());

        if (room != null) {
            room.setStatus("BOOKED");
            roomRepository.save(room);
        }

        return "redirect:/bookings";
    }
    

    // View All Bookings
    @GetMapping("/bookings")
    public String listBookings(Model model) {
        model.addAttribute("bookings", bookingRepository.findAll());
        return "bookings";
    }
}
