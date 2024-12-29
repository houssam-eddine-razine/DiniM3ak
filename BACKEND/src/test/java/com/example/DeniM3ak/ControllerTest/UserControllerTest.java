package com.example.DeniM3ak.ControllerTest;

import com.example.DeniM3ak.model.User;
import com.example.DeniM3ak.repository.UserRepository;
import com.example.DeniM3ak.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void testSignup() throws Exception {
        // Test user registration
        String userJson = """
                {
                    "email": "test@example.com",
                    "password": "password123",
                    "name": "John",
                    "firstname": "Doe",
                    "phone": "123456789"
                }
                """;

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("test@example.com")));
    }

    @Test
    void testLogin() throws Exception {
        // Create a user for testing
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(jwtUtil.hashPassword("password123"));
        user.setId("12345"); // Mocked user ID
        userRepository.save(user);

        String loginJson = """
        {
            "email": "test@example.com",
            "password": "password123"
        }
    """;

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue())); // Token should be returned
    }

    @Test
    void testGenerateToken() {
        User user = new User();
        user.setId("12345");
        user.setEmail("test@example.com");

        String token = jwtUtil.generateToken(user);

        assertNotNull(token); // Ensure a token is generated
    }
}
