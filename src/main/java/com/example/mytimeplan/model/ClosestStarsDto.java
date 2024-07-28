package com.example.mytimeplan.model;

import lombok.Data;

import java.util.List;

@Data
public class ClosestStarsDto {
    private final List<Star> stars;
    private final int size;
}
