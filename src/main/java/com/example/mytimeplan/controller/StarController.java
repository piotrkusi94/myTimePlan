package com.example.mytimeplan.controller;

import com.example.mytimeplan.model.ClosestStarsDto;
import com.example.mytimeplan.model.Star;
import com.example.mytimeplan.service.StarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stars")
@RequiredArgsConstructor
public class StarController {
    private final StarService starService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Star> getStarById(@PathVariable Long id) {
        return ResponseEntity.ok(starService.getStarById(id));
    }
    @PostMapping
    public ResponseEntity<Star> addNewStar(@RequestBody Star star) {
        return ResponseEntity.ok(starService.addNewStar(star));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Star> updateStar(@PathVariable Long id, @RequestBody Star star) {
        return ResponseEntity.ok(starService.updateStar(star, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteStar(@PathVariable Long id) {
        return ResponseEntity.ok(starService.deleteStar(id));
    }

    @GetMapping("/closest")
    public ResponseEntity<List<Star>> findClosestStars(@RequestBody ClosestStarsDto dto) {
        return ResponseEntity.ok(starService.findClosestStars(dto.getStars(), dto.getSize()));
    }

    @GetMapping("/distances")
    public ResponseEntity<Map<Long, Integer>> getNumberOfStarsByDistances(@RequestBody List<Star> stars) {
        return ResponseEntity.ok(starService.getNumberOfStarsByDistances(stars));
    }

    @GetMapping("/unique")
    public ResponseEntity<Collection<Star>> getUniqueStars(@RequestBody Collection<Star> stars) {
        return ResponseEntity.ok(starService.getUniqueStars(stars));
    }
}
