package com.example.mytimeplan.controller;

import com.example.mytimeplan.model.ClosestStarsDto;
import com.example.mytimeplan.model.Star;
import com.example.mytimeplan.service.StarService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(StarController.class)
class StarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StarService starService;

    @Autowired
    private ObjectMapper objectMapper;

    private Star star;

    @BeforeEach
    void setUp() {
        star = new Star("Star A", 1000L);
    }

    @Test
    void getStarByIdTest() throws Exception {
        when(starService.getStarById(1L)).thenReturn(star);

        mockMvc.perform(get("/api/stars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Star A"))
                .andExpect(jsonPath("$.distance").value(1000L));
    }

    @Test
    void addNewStarTest() throws Exception {
        when(starService.addNewStar(any(Star.class))).thenReturn(star);

        mockMvc.perform(post("/api/stars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(star)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Star A"))
                .andExpect(jsonPath("$.distance").value(1000L));
    }

    @Test
    void updateStarTest() throws Exception {
        when(starService.updateStar(any(Star.class), any(Long.class))).thenReturn(star);

        mockMvc.perform(put("/api/stars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(star)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Star A"))
                .andExpect(jsonPath("$.distance").value(1000L));
    }

    @Test
    void deleteStarTest() throws Exception {
        when(starService.deleteStar(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/stars/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void findClosestStarsTest() throws Exception {
        List<Star> stars = Arrays.asList(new Star("Star A", 1000L), new Star("Star B", 500L));
        ClosestStarsDto dto = new ClosestStarsDto(stars, 2);

        when(starService.findClosestStars(any(List.class), any(Integer.class))).thenReturn(stars);

        mockMvc.perform(get("/api/stars/closest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Star A"))
                .andExpect(jsonPath("$[0].distance").value(1000L))
                .andExpect(jsonPath("$[1].name").value("Star B"))
                .andExpect(jsonPath("$[1].distance").value(500L));
    }

    @Test
    void getNumberOfStarsByDistancesTest() throws Exception {
        List<Star> stars = Arrays.asList(new Star("Star A", 1000L), new Star("Star B", 500L));
        Map<Long, Integer> result = Map.of(1000L, 1, 500L, 1);

        when(starService.getNumberOfStarsByDistances(any(List.class))).thenReturn(result);

        mockMvc.perform(get("/api/stars/distances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stars)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.1000").value(1))
                .andExpect(jsonPath("$.500").value(1));
    }

    @Test
    void getUniqueStarsTest() throws Exception {
        Collection<Star> stars = Arrays.asList(new Star("Star A", 1000L), new Star("Star B", 500L));
        Collection<Star> uniqueStars = Collections.singleton(new Star("Star A", 1000L));

        when(starService.getUniqueStars(any(Collection.class))).thenReturn(uniqueStars);

        mockMvc.perform(get("/api/stars/unique")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stars)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Star A"))
                .andExpect(jsonPath("$[0].distance").value(1000L));
    }

}