package com.exercise.api.service;

import com.exercise.api.model.Exercise;
import com.exercise.api.model.Workout;
import com.exercise.api.repository.WorkoutRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WorkoutServiceTest {

    @Mock
    private WorkoutRepository workoutRepository;

    @InjectMocks
    private WorkoutService workoutService;

    @Test
    void testGetAllWorkouts() {
        // Initialize workouts
        List<Workout> workouts = new ArrayList<>();
        workouts.add(Mockito.mock(Workout.class));
        workouts.add(Mockito.mock(Workout.class));
        // Test
        when(workoutRepository.findAll()).thenReturn(workouts);
        List<Workout> allWorkouts = workoutService.getAllWorkouts();
        assertEquals(workouts, allWorkouts);
    }

    @Test
    void testGetWorkoutById() throws Exception {
        Workout workout = Mockito.mock(Workout.class);
        when(workoutRepository.findById(anyLong())).thenReturn(java.util.Optional.of(workout));
        Workout result = workoutService.getWorkoutById(0L);
        assertEquals(workout, result);
    }

    @Test
    void testGetWorkoutById_shouldThrowException() {
        assertThrows(Exception.class, () -> workoutService.getWorkoutById(-3));
    }

    @Test
    void testGetBetweenDates() throws Exception {
        Date dateOne = Mockito.mock(Date.class);
        Date dateTwo = Mockito.mock(Date.class);
        // Initialize workouts
        List<Workout> workouts = new ArrayList<>();
        workouts.add(Mockito.mock(Workout.class));
        workouts.add(Mockito.mock(Workout.class));
        // Test
        when(workoutRepository.findByDateBetween(any(Date.class), any(Date.class))).thenReturn(workouts);
        List<Workout> result = workoutService.getBetweenDates(dateOne, dateTwo);
        assertEquals(workouts, result);
    }

    @Test
    void testGetBetweenDates_shouldThrowException() throws Exception {
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date dateOne = formatter.parse("2021/01/01");
        Date dateTwo = formatter.parse("2021/01/02");
        assertThrows(Exception.class, () -> workoutService.getBetweenDates(dateTwo, dateOne));
    }

    @Test
    void testGetWorkoutsByUserId() throws Exception {
        List<Workout> workouts = new ArrayList<>();
        workouts.add(Mockito.mock(Workout.class));
        workouts.add(Mockito.mock(Workout.class));
        // Test
        when(workoutRepository.findWorkoutByUserId(anyLong())).thenReturn(workouts);
        List<Workout> result = workoutService.getWorkoutsByUserId(0L);
        assertEquals(workouts, result);
    }

    @Test
    void testGetWorkoutsByUserId_shouldThrowException() {
        assertThrows(Exception.class, () -> workoutService.getWorkoutsByUserId(-3));
    }

    @Test
    void testGetWorkoutIdByUserId() throws Exception {
        when(workoutRepository.findWorkoutIdByUserId(anyLong())).thenReturn(Arrays.<Long>asList(Long.valueOf(1)));
        List<Long> result = workoutService.getWorkoutIdByUserId(0L);
        assertEquals(Arrays.<Long>asList(Long.valueOf(1)), result);
    }

    @Test
    void testGetWorkoutIdByUserId_shouldThrowException() {
        assertThrows(Exception.class, () -> workoutService.getWorkoutIdByUserId(-3));
    }

    @Test
    void testSaveOrUpdate() throws Exception {
        Workout workout = Mockito.mock(Workout.class);
        when(workoutRepository.save(any(Workout.class))).thenReturn(workout);
        workoutService.saveOrUpdate(workout);
        verify(workoutRepository).save(workout);
    }

    @Test
    void testSaveOrUpdate_shouldThrowException() {
        List<Exercise> exercises = new ArrayList<>();
        exercises.add(Mockito.mock(Exercise.class));
        Workout workout = new Workout(-3, 1, Mockito.mock(Date.class), "10 minutes", exercises, 2);
        assertThrows(Exception.class, () -> workoutService.saveOrUpdate(workout));
    }

    @Test
    void testDelete() throws Exception {
        Workout workout = Mockito.mock(Workout.class);
        when(workoutRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(workout));
        workoutService.delete(workout.getWorkoutId());
        verify(workoutRepository).deleteById(workout.getWorkoutId());
    }

    @Test
    void testDelete_shouldThrowException() {
        assertThrows(Exception.class, () -> workoutService.delete(-3));
    }

}
