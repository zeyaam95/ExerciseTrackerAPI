package com.exercise.api.service;

import com.exercise.api.model.Exercise;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExerciseServiceTest {

    @Mock
    private ExerciseService exerciseService;

    @Test
    void getAllExercises() {
    }

    @Test
    void getExerciseById() {
        Exercise exercise = new Exercise(3L, 140F, "10", "Running","Cardio","Speed: 10mph", 1, 1);
        Mockito.when(exerciseService.getExerciseById(anyLong())).thenReturn(exercise);
        assertEquals(exercise, exerciseService.getExerciseById(3L));
    }

    @Test
    void saveOrUpdate() {
    }

    @Test
    void delete() {
    }

    @Test
    void getExerciseByWorkoutId() {
    }

    @Test
    void getExerciseByWorkoutIdAndType() {
    }
}