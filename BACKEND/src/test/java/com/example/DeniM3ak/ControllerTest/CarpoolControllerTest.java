package com.example.DeniM3ak.ControllerTest;

import com.example.DeniM3ak.model.Carpool;
import com.example.DeniM3ak.model.User;
import com.example.DeniM3ak.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CarpoolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarpoolRepository carpoolRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void testPublishCarpool() throws Exception {
        // Create a User object to pass to generateToken
        User user = new User();
        user.setId("driver-id"); // Set the user ID
        user.setEmail("driver@example.com"); // Set the email
        user.setPassword("password123"); // Optional: Set password if required for other parts

        // Generate a token using the User object
        String token = jwtUtil.generateToken(user);

        // JSON payload for carpool
        String carpoolJson = """
        {
            "departure": "CityA",
            "arrival": "CityB",
            "date": "2023-10-10",
            "seats": 3,
            "price": 50,
            "description": "Sample carpool"
        }
        """;

        // Perform the request (mockMvc should be configured)
        mockMvc.perform(post("/api/carpool/publish")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carpoolJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.departure", is("CityA")));
    }

    @Test
    void testGetCarpoolById() throws Exception {
        // Create a carpool
        Carpool carpool = new Carpool();
        carpool.setDeparture("CityA");
        carpool.setArrival("CityB");
        carpool.setDate(new Date());
        carpool.setSeats(3);
        carpool.setPrice(50);
        carpoolRepository.save(carpool);

        // Perform GET request
        mockMvc.perform(get("/api/carpool/" + carpool.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.departure", is("CityA")));
    }
}
