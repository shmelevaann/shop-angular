package ru.chiffa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.chiffa.controllers.UserController;
import ru.chiffa.dto.JwtRequest;

import java.security.Principal;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindAddresses() throws Exception {
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "User1";
            }
        };

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/addresses")
                .contentType(MediaType.APPLICATION_JSON)
                .principal(principal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)));

    }
 }