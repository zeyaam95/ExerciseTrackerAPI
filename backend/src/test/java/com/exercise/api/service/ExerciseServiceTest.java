package com.exercise.api.service;

import com.exercise.api.model.Exercise;
import com.exercise.api.repository.ExerciseRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ExerciseServiceTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private ExerciseService exerciseService;

    @Test
    void testGetAllExercises() {
        // Initialize exercises
        List<Exercise> exercises = new ArrayList<>();
        Exercise exerciseOne = new Exercise(1, 200, "10 minutes", "Deadlift", "Strength", "None", 3, 3);
        Exercise exerciseTwo = new Exercise(2, 200, "10 minutes", "Bench Press", "Strength", "None", 3, 3);
        exercises.add(exerciseOne);
        exercises.add(exerciseTwo);
        // Test
        when(exerciseRepository.findAll()).thenReturn(exercises);
        assertEquals(exercises, exerciseService.getAllExercises());
    }

    @Test
    void testGetExerciseById() throws Exception {
        Exercise exercise = new Exercise(3L, 140F, "10", "Running","Cardio","Speed: 10mph", 1, 1);
        when(exerciseRepository.findById(anyLong())).thenReturn(java.util.Optional.of(exercise));
        assertEquals(exercise, exerciseService.getExerciseById(3L));
    }

    @Test
    void testGetExerciseById_shouldThrowException() {
        assertThrows(Exception.class, () -> exerciseService.getExerciseById(-3));
    }

    @Test
    void testSaveOrUpdate() throws Exception {
        Exercise exercise = new Exercise(3L, 140F, "10", "Running","Cardio","Speed: 10mph", 1, 1);
        when(exerciseRepository.save(exercise)).thenReturn(exercise);
        exerciseService.saveOrUpdate(exercise);
        verify(exerciseRepository).save(exercise);
    }

    @Test
    void testSaveOrUpdate_shouldThrowException() {
        Exercise exercise = new Exercise(-3, 140F, "10", "Running","Cardio","Speed: 10mph", 1, 1);
        assertThrows(Exception.class, () -> exerciseService.saveOrUpdate(exercise));
    }

    @Test
    void testDelete() throws Exception {
        List<Exercise> exercises = new ArrayList<>();
        Exercise exercise = new Exercise(3L, 140F, "10", "Running","Cardio","Speed: 10mph", 1, 1);
        exercises.add(exercise);
        when(exerciseRepository.findExerciseByWorkoutId(exercise.getExerciseId())).thenReturn(exercises);
        exerciseService.delete(exercise.getExerciseId());
        verify(exerciseRepository).deleteById(exercise.getExerciseId());
    }

    @Test
    void testDelete_shouldThrowException() {
        assertThrows(Exception.class, () -> exerciseService.delete(-3));
    }

    @Test
    void testGetExerciseByWorkoutId() throws Exception {
        List<Exercise> exercises = new ArrayList<>();
        Exercise exercise = new Exercise(3L, 140F, "10", "Running","Cardio","Speed: 10mph", 1, 1);
        exercises.add(exercise);
        when(exerciseRepository.findExerciseByWorkoutId(anyLong())).thenReturn(exercises);
        assertEquals(exercises, exerciseService.getExerciseByWorkoutId(0L));
    }

    @Test
    void testGetExerciseByWorkoutId_shouldThrowException() {
        assertThrows(Exception.class, () -> exerciseService.getExerciseByWorkoutId(-3));
    }

    @Test
    void testGetExerciseByWorkoutIdAndType() throws Exception {
        List<Exercise> exercises = new ArrayList<>();
        Exercise exercise = new Exercise(3L, 140F, "10", "Running","Cardio","Speed: 10mph", 1, 1);
        exercises.add(exercise);
        when(exerciseRepository.findExerciseByWorkoutIdAndType(anyLong(), anyString())).thenReturn(exercises);
        assertEquals(exercises, exerciseService.getExerciseByWorkoutIdAndType(0L, "Cardio"));
    }

    @Test
    void testGetExerciseByWorkoutIdAndType_shouldThrowException() throws Exception {
        assertThrows(Exception.class, () -> exerciseService.getExerciseByWorkoutIdAndType(-3, "Lit"));
    }
}