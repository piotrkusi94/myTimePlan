package com.example.mytimeplan.mapper;

import com.example.mytimeplan.model.Star;
import com.example.mytimeplan.model.StarJpa;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StarMapperTest {
    private final StarMapper starMapper = new StarMapper();

    @Test
    void testMapToStar() {
        //given
        String name = "name";
        Long distance = 1L;
        StarJpa starJpa = new StarJpa(1L, name, distance);
        //when
        Star star = starMapper.mapToStar(starJpa);
        //then
        assertThat(star.getDistance()).isEqualTo(distance);
        assertThat(star.getName()).isEqualTo(name);
    }

    @Test
    void testMapToStarJpa() {
        //given
        String name = "name";
        Long distance = 1L;
        Star star = new Star(name, distance);
        //when
        StarJpa starJpa = starMapper.mapToStarJpa(star);
        //then
        assertThat(starJpa.getDistance()).isEqualTo(distance);
        assertThat(starJpa.getName()).isEqualTo(name);
    }

}