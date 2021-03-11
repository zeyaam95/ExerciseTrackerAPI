package com.exercise.api.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long exerciseId;
    private float calories;
    private String duration;
    private String name;
    private String type;
    private String notes;
    private int reps;
    private int sets;
}
