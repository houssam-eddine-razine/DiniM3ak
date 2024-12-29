package com.example.DeniM3ak.controller;

import com.example.DeniM3ak.model.Carpool;
import com.example.DeniM3ak.repository.CarpoolRepository;
import com.example.DeniM3ak.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/carpool")
public class BookingController {

    @Autowired
    private CarpoolRepository carpoolRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/{id}/book")
    public ResponseEntity<?> bookCarpool(@PathVariable String id, @RequestHeader("Authorization") String token) {
        String userId = jwtUtil.extractUserId(token); // Extract user ID from JWT token

        Optional<Carpool> carpoolOptional = carpoolRepository.findById(id);

        if (carpoolOptional.isEmpty()) {
            return ResponseEntity.status(404).body("Carpool not found");
        }

        Carpool carpool = carpoolOptional.get();

        // Check if there are available seats
        if (carpool.getPassengers().size() >= carpool.getSeats()) {
            return ResponseEntity.status(400).body("Carpool is full");
        }

        // Add the user to the carpool
        carpool.getPassengers().add(userId);
        carpoolRepository.save(carpool);

        return ResponseEntity.ok("Booking successful");
    }
}