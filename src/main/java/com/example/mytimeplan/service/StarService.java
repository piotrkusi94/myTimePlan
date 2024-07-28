package com.example.mytimeplan.service;

import com.example.mytimeplan.model.Star;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface StarService {
    List<Star> findClosestStars(List<Star>stars, int size);
    Map<Long, Integer> getNumberOfStarsByDistances(List<Star> stars);
    Collection<Star> getUniqueStars(Collection<Star> stars);
    Star getStarById(Long id);
    Star addNewStar(Star star);
    Star updateStar(Star star, Long id);
    boolean deleteStar(Long id);
}
