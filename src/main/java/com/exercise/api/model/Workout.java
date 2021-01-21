package com.exercise.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long workoutId;
    private long userId;
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT-05:00")
    private Date date;
    private String totalDuration;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Exercise> exercises;
    private int numExercises;
}
