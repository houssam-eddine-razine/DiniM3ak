package com.example.DeniM3ak.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class FileController {
    @PostMapping("/picture")
    public ResponseEntity<?> uploadPicture(@RequestParam("file") MultipartFile file) {
        try {
            String uploadDir = "uploads/";
            File destination = new File(uploadDir + file.getOriginalFilename());
            file.transferTo(destination);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File upload failed");
        }
    }
}
