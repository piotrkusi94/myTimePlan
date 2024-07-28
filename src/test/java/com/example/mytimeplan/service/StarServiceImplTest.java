package com.example.mytimeplan.service;

import com.example.mytimeplan.exception.StarNotFoundException;
import com.example.mytimeplan.mapper.StarMapper;
import com.example.mytimeplan.model.Star;
import com.example.mytimeplan.model.StarJpa;
import com.example.mytimeplan.repository.StarRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@MockitoSettings
class StarServiceImplTest {
    @Mock
    private StarRepository starRepository;

    @Mock
    private StarMapper starMapper;

    @InjectMocks
    private StarServiceImpl starService;


    @Test
    void findClosestStarsTest() {
        List<Star> stars = Arrays.asList(
                new Star("Star A", 1000L),
                new Star("Star B", 500L),
                new Star("Star C", 2000L)
        );
        List<Star> closestStars = starService.findClosestStars(stars, 2);

        assertEquals(2, closestStars.size());
        assertEquals("Star B", closestStars.get(0).getName());
        assertEquals("Star A", closestStars.get(1).getName());
    }

    @Test
    void getNumberOfStarsByDistancesTest() {
        List<Star> stars = Arrays.asList(
                new Star("Star A", 1000L),
                new Star("Star B", 500L),
                new Star("Star C", 1000L)
        );
        Map<Long, Integer> result = starService.getNumberOfStarsByDistances(stars);

        assertEquals(2, result.size());
        assertEquals(2, result.get(1000L));
        assertEquals(1, result.get(500L));
    }

    @Test
    void getUniqueStarsTest() {
        Collection<Star> stars = Arrays.asList(
                new Star("Star A", 1000L),
                new Star("Star B", 500L),
                new Star("Star A", 2000L)
        );
        Collection<Star> uniqueStars = starService.getUniqueStars(stars);

        assertEquals(2, uniqueStars.size());
    }

    @Test
    void getStarByIdTest() {
        Long id = 1L;
        StarJpa starJpa = new StarJpa();
        starJpa.setId(id);
        starJpa.setName("Star A");
        starJpa.setDistance(1000L);
        when(starRepository.findById(id)).thenReturn(Optional.of(starJpa));
        when(starMapper.mapToStar(starJpa)).thenReturn(new Star("Star A", 1000L));

        Star star = starService.getStarById(id);
        assertNotNull(star);
        assertEquals("Star A", star.getName());
        assertEquals(1000, star.getDistance());
    }

    @Test
    void getStarByIdNotFoundTest() {
        Long id = 1L;
        when(starRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(StarNotFoundException.class, () -> starService.getStarById(id));
    }

    @Test
    void addNewStarTest() {
        Star star = new Star("Star A", 1000L);
        StarJpa starJpa = new StarJpa();
        starJpa.setName("Star A");
        starJpa.setDistance(1000L);

        when(starMapper.mapToStarJpa(star)).thenReturn(starJpa);
        when(starRepository.save(starJpa)).thenReturn(starJpa);
        when(starMapper.mapToStar(starJpa)).thenReturn(star);

        Star newStar = starService.addNewStar(star);
        assertNotNull(newStar);
        assertEquals("Star A", newStar.getName());
        assertEquals(1000, newStar.getDistance());
    }

    @Test
    void updateStarTest() {
        Long id = 1L;
        Star star = new Star("Star B", 500L);
        StarJpa starJpa = new StarJpa();
        starJpa.setId(id);
        starJpa.setName("Star A");
        starJpa.setDistance(1000L);

        when(starRepository.findById(id)).thenReturn(Optional.of(starJpa));
        when(starRepository.save(starJpa)).thenReturn(starJpa);
        when(starMapper.mapToStar(starJpa)).thenReturn(new Star("Star B", 500L));

        Star updatedStar = starService.updateStar(star, id);
        assertNotNull(updatedStar);
        assertEquals("Star B", updatedStar.getName());
        assertEquals(500, updatedStar.getDistance());
    }

    @Test
    void updateStarNotFoundTest() {
        Long id = 1L;
        Star star = new Star("Star B", 500L);

        when(starRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(StarNotFoundException.class, () -> starService.updateStar(star, id));
    }

    @Test
    void deleteStarTest() {
        Long id = 1L;
        when(starRepository.findById(id)).thenReturn(Optional.empty());

        boolean result = starService.deleteStar(id);
        assertTrue(result);
    }

    @Test
    void deleteStarNotFoundTest() {
        Long id = 1L;
        doThrow(new StarNotFoundException()).when(starRepository).deleteById(id);

        assertThrows(StarNotFoundException.class, () -> starService.deleteStar(id));
    }
}