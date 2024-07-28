package com.example.mytimeplan.service;

import com.example.mytimeplan.exception.StarNotFoundException;
import com.example.mytimeplan.mapper.StarMapper;
import com.example.mytimeplan.model.Star;
import com.example.mytimeplan.model.StarJpa;
import com.example.mytimeplan.repository.StarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StarServiceImpl implements StarService {
    private final StarRepository starRepository;
    private final StarMapper starMapper;


    @Override
    public List<Star> findClosestStars(List<Star> stars, int size) {
        return stars.stream()
                .sorted(Comparator.comparingLong(Star::getDistance))
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public Map<Long, Integer> getNumberOfStarsByDistances(List<Star> stars) {
        return stars.stream()
                .collect(Collectors.groupingBy(Star::getDistance, TreeMap::new, Collectors.reducing(0, e -> 1, Integer::sum)));
    }

    @Override
    public Collection<Star> getUniqueStars(Collection<Star> stars) {
        return new HashSet<>(stars);
    }

    @Override
    public Star getStarById(Long id) {
        StarJpa starJpa = starRepository.findById(id).orElseThrow(StarNotFoundException::new);
        return starMapper.mapToStar(starJpa);
    }

    @Override
    public Star addNewStar(Star star) {
        StarJpa saved = starRepository.save(starMapper.mapToStarJpa(star));
        return starMapper.mapToStar(saved);
    }

    @Override
    public Star updateStar(Star star, Long id) {
        StarJpa starJpa = starRepository.findById(id).orElseThrow(StarNotFoundException::new);
        starJpa.setDistance(star.getDistance());
        starJpa.setName(star.getName());
        starRepository.save(starJpa);
        return starMapper.mapToStar(starJpa);
    }

    @Override
    public boolean deleteStar(Long id) {
        starRepository.deleteById(id);
        return starRepository.findById(id).isEmpty();
    }
}
