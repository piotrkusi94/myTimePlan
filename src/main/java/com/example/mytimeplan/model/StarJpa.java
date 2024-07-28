package com.example.mytimeplan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor
@Getter
@Setter
public class StarJpa {
    @Id
    private Long id;
    private String name;
    private Long distance;
}
