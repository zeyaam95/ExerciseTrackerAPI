package com.exercise.api.controller;

import com.exercise.api.model.Exercise;
import com.exercise.api.model.Workout;
import com.exercise.api.service.ExerciseService;
import com.exercise.api.service.WorkoutService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExerciseControllerTest {

    @LocalServerPort
    int port;

    @MockBean
    private ExerciseService exerciseService;

    @MockBean
    private WorkoutService workoutService;

    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper mapper;
    List<Exercise> exercises;
    Exercise exerciseOne, exerciseTwo;

    @BeforeEach
    void loadEntities() {
        this.mapper = new ObjectMapper();
        this.exercises = new ArrayList<>();
        this.exerciseOne = new Exercise(1, 200, "10 minutes", "Deadlift", "Strength", "None", 3, 3);
        this.exerciseTwo = new Exercise(2, 120, "10 minutes", "Running", "Cardio", "None", 1, 1);
        this.exercises.add(exerciseOne);
        this.exercises.add(exerciseTwo);
    }

    @Test
    void testGetAllExercises() {
        final String uri = "http://localhost:" + port + "/exercise/";
        when(exerciseService.getAllExercises()).thenReturn(this.exercises);
        JsonNode result = restTemplate.getForEntity(uri, JsonNode.class).getBody();
        List<Exercise> resultExercises = this.mapper.convertValue(result, new TypeReference<List<Exercise>>(){});
        assertEquals(resultExercises, this.exercises);
    }

    @Test
    void testGetExercise() throws Exception {
        final String uri = "http://localhost:" + port + "/exercise/1";
        when(exerciseService.getExerciseById(anyLong())).thenReturn(this.exerciseOne);
        JsonNode result = restTemplate.getForEntity(uri, JsonNode.class).getBody();
        Exercise resultExercise = this.mapper.convertValue(result, Exercise.class);
        assertEquals(resultExercise, this.exerciseOne);
    }

    @Test
    void testGetExerciseByWorkout() throws Exception {
        final String uri = "http://localhost:" + port + "/exercise/workout/1";
        when(exerciseService.getExerciseByWorkoutId(anyLong())).thenReturn(this.exercises);
        JsonNode result = restTemplate.getForEntity(uri, JsonNode.class).getBody();
        List<Exercise> resultExercises = this.mapper.convertValue(result, new TypeReference<List<Exercise>>(){});
        assertEquals(resultExercises, this.exercises);
    }

    @Test
    void testGetExerciseByWorkoutAndType() throws Exception {
        final String uri = "http://localhost:" + port + "/exercise/workout/1/Cardio";
        List<Exercise> cardioExercises = new ArrayList<>();
        cardioExercises.add(this.exerciseTwo);
        when(exerciseService.getExerciseByWorkoutIdAndType(anyLong(), eq("Cardio"))).thenReturn(cardioExercises);
        JsonNode result = restTemplate.getForEntity(uri, JsonNode.class).getBody();
        List<Exercise> resultExercises = this.mapper.convertValue(result, new TypeReference<List<Exercise>>(){});
        assertEquals(resultExercises, cardioExercises);
    }

    @Test
    void testGetAllExercisesByUserId() throws Exception {
        final String uri = "http://localhost:" + port + "/exercise/user/1";
        Workout workout = new Workout(1, 1, Mockito.mock(Date.class), "10 minutes", this.exercises, 2);
        List<Workout> workouts= new ArrayList<>();
        workouts.add(workout);
        when(workoutService.getWorkoutsByUserId(anyLong())).thenReturn(workouts);
        when(exerciseService.getExerciseByWorkoutId(anyLong())).thenReturn(this.exercises);
        JsonNode result = restTemplate.getForEntity(uri, JsonNode.class).getBody();
        List<Exercise> resultExercises = this.mapper.convertValue(result, new TypeReference<List<Exercise>>(){});
        assertEquals(resultExercises, this.exercises);
    }

    @Test
    void testGetAllExercisesByUserIdAndType() throws Exception {
        final String uri = "http://localhost:" + port + "/exercise/user/1/Cardio";
        Workout workout = new Workout(1, 1, Mockito.mock(Date.class), "10 minutes", this.exercises, 2);
        List<Workout> workouts= new ArrayList<>();
        workouts.add(workout);
        List<Exercise> cardioExercises = new ArrayList<>();
        cardioExercises.add(this.exerciseTwo);
        when(workoutService.getWorkoutsByUserId(anyLong())).thenReturn(workouts);
        when(exerciseService.getExerciseByWorkoutIdAndType(anyLong(), eq("Cardio"))).thenReturn(cardioExercises);
        JsonNode result = restTemplate.getForEntity(uri, JsonNode.class).getBody();
        List<Exercise> resultExercises = this.mapper.convertValue(result, new TypeReference<List<Exercise>>(){});
        assertEquals(resultExercises, cardioExercises);
    }

    @Test
    void deleteExercise() {
        final String uri = "http://localhost:" + port + "/exercise/1";
        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.DELETE, null, String.class);
        assertNotNull(result);
        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void saveExercise(){
        final String uri = "http://localhost:" + port + "/exercise/";
        Exercise exercise = this.exerciseOne;
        exercise.setCalories(300);
        long result = restTemplate.postForObject(uri, exercise, long.class);
        assertEquals(1, result);
    }
}