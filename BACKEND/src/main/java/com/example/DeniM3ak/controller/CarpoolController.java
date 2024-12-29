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
public class CarpoolController {

    @Autowired
    private CarpoolRepository carpoolRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/publish")
    public ResponseEntity<?> publishCarpool(@RequestBody Carpool carpool, @RequestHeader("Authorization") String token) {
        // Extract user ID from token
        String userId = jwtUtil.extractUserId(token);
        carpool.setDriver(userId);
        carpoolRepository.save(carpool);
        return ResponseEntity.status(201).body(carpool);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCarpoolById(@PathVariable String id) {
        Optional<Carpool> carpoolOptional = carpoolRepository.findById(id);

        if (carpoolOptional.isPresent()) {
            return ResponseEntity.ok(carpoolOptional.get());
        } else {
            return ResponseEntity.status(404).body("Carpool not found");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateCarpool(@PathVariable String id, @RequestBody Carpool updatedCarpool, @RequestHeader("Authorization") String token) {
        String userId = jwtUtil.extractUserId(token);
        Optional<Carpool> existingCarpool = carpoolRepository.findById(id);

        if (existingCarpool.isPresent() && existingCarpool.get().getDriver().equals(userId)) {
            updatedCarpool.setId(id); // Ensure the ID remains the same
            updatedCarpool.setDriver(userId);
            carpoolRepository.save(updatedCarpool);
            return ResponseEntity.ok(updatedCarpool);
        }

        return ResponseEntity.status(401).body("Unauthorized or carpool not found");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarpool(@PathVariable String id, @RequestHeader("Authorization") String token) {
        String userId = jwtUtil.extractUserId(token);
        Optional<Carpool> carpool = carpoolRepository.findById(id);

        if (carpool.isPresent() && carpool.get().getDriver().equals(userId)) {
            carpoolRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(401).body("Unauthorized or carpool not found");
    }
}
