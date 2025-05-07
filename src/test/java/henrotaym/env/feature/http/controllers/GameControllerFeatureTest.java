package henrotaym.env.feature.http.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import henrotaym.env.ApplicationTest;
import henrotaym.env.repositories.GameRepository;

public class GameControllerFeatureTest extends ApplicationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    GameRepository gameRepository;

    @Test
    public void it_responds_to_store_url() throws Exception {
        String name = "test";
        Map<String, String> body = Map.of("name", name);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/games")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(body));

        this.mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name));

        assertEquals(1, this.gameRepository.count());
    }
}
