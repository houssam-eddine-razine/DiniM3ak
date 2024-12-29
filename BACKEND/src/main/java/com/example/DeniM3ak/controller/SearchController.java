package com.example.DeniM3ak.controller;

import com.example.DeniM3ak.model.Carpool;
import com.example.DeniM3ak.repository.CarpoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private CarpoolRepository carpoolRepository;

    @GetMapping("/")
    public List<Carpool> getAllCarpools() {
        return carpoolRepository.findAll();
    }

    @GetMapping("/{departure}/{arrival}")
    public List<Carpool> searchCarpools(
            @PathVariable String departure,
            @PathVariable String arrival,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
            @RequestParam(required = false) Integer seats) {

        return carpoolRepository.findByDepartureAndArrivalAndDateAndSeats(
                departure, arrival, date != null ? date : new Date(), seats != null ? seats : 1);
    }
}
