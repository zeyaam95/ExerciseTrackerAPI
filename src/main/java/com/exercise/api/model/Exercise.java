package com.exercise.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long exerciseId;
    private float calories;
    private String duration;
    private String name;
    private String type;
    private int reps;
    private int sets;
    private String notes;
}