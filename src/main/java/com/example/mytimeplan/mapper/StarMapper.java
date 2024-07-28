package com.example.mytimeplan.mapper;

import com.example.mytimeplan.model.Star;
import com.example.mytimeplan.model.StarJpa;
import org.springframework.stereotype.Component;

@Component
public class StarMapper {
    public Star mapToStar(StarJpa starJpa) {
        return Star.builder()
                .name(starJpa.getName())
                .distance(starJpa.getDistance())
                .build();
    }
    public StarJpa mapToStarJpa(Star star) {
        return StarJpa.builder()
                .name(star.getName())
                .distance(star.getDistance())
                .build();
    }
}
