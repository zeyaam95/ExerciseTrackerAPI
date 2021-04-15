package com.exercise.api.controller;

import com.exercise.api.model.Exercise;
import com.exercise.api.model.Workout;
import com.exercise.api.service.WorkoutService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@JsonIgnoreProperties(ignoreUnknown = true)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class WorkoutControllerTest {

    @LocalServerPort
    int port;

    @MockBean
    private WorkoutService workoutService;

    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper mapper;
    List<Workout> workouts;
    Workout workoutOne, workoutTwo;

    @BeforeEach
    void loadEntities() throws Exception {
        this.workouts = new ArrayList<>();
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.workouts = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = formatter.parse("01-Jan-1990");
        List<Exercise> exercises = new ArrayList<>();
        Exercise exerciseOne = new Exercise(1, 200, "10 minutes", "Deadlift", "Strength", "None", 3, 3);
        Exercise exerciseTwo = new Exercise(2, 120, "10 minutes", "Running", "Cardio", "None", 1, 1);
        exercises.add(exerciseOne);
        exercises.add(exerciseTwo);
        this.workoutOne = new Workout(1, 1, date, "10 minutes", exercises, 2);
        this.workoutTwo = new Workout(2, 2, date, "20 minutes", exercises, 2);
        this.workouts.add(workoutOne);
        this.workouts.add(workoutTwo);
    }

    @Test
    void getAllWorkouts() {
        final String uri = "http://localhost:" + port + "/workout/";
        when(workoutService.getAllWorkouts()).thenReturn(this.workouts);
        List<Workout> result = restTemplate.getForEntity(uri, List.class).getBody();
        List<Workout> resultWorkouts = this.mapper.convertValue(result, new TypeReference<List<Workout>>(){});
        assertEquals(resultWorkouts, this.workouts);
    }

    @Test
    void getWorkout() throws Exception {
        final String uri = "http://localhost:" + port + "/workout/1";
        when(workoutService.getWorkoutById(anyLong())).thenReturn(this.workoutOne);
        JsonNode result = restTemplate.getForEntity(uri, JsonNode.class).getBody();
        Workout resultWorkouts = this.mapper.convertValue(result, Workout.class);
        assertEquals(resultWorkouts, this.workoutOne);
    }

    @Test
    void getBetweenDates() throws Exception {
        final String uri = "http://localhost:" + port + "/workout/dates/2021-01-01/2022-01-01";
        when(workoutService.getBetweenDates(any(Date.class), any(Date.class))).thenReturn(this.workouts);
        JsonNode result = restTemplate.getForEntity(uri, JsonNode.class).getBody();
        List<Workout> resultWorkouts = this.mapper.convertValue(result, new TypeReference<List<Workout>>(){});
        assertEquals(resultWorkouts, this.workouts);
    }

    @Test
    void getWorkoutsByUserId() throws Exception {
        final String uri = "http://localhost:" + port + "/workout/user/1";
        when(workoutService.getWorkoutsByUserId(anyLong())).thenReturn(this.workouts.subList(0,1));
        JsonNode result = restTemplate.getForEntity(uri, JsonNode.class).getBody();
        List<Workout> resultWorkouts = this.mapper.convertValue(result, new TypeReference<List<Workout>>(){});
        assertEquals(resultWorkouts, this.workouts.subList(0,1));
    }

    @Test
    void getWorkoutIdsByUserId() throws Exception {
        final String uri = "http://localhost:" + port + "/workout/user/1/idonly";
        List<Long> workoutIds = new ArrayList<>();
        workoutIds.add(1L);
        when(workoutService.getWorkoutIdByUserId(anyLong())).thenReturn(workoutIds);
        JsonNode result = restTemplate.getForEntity(uri, JsonNode.class).getBody();
        List<Long> resultWorkoutIds = this.mapper.convertValue(result, new TypeReference<List<Long>>(){});
        assertEquals(resultWorkoutIds, workoutIds);
    }

    @Test
    void deleteWorkout() {
        final String uri = "http://localhost:" + port + "/workout/1";
        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.DELETE, null, String.class);
        assertNotNull(result);
        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void saveWorkout() {
        this.mapper.setVisibility(mapper.getVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        final String uri = "http://localhost:" + port + "/workout/";
        Workout workout = this.workoutOne;
        workout.setTotalDuration("20 minutes");
        long result = restTemplate.postForObject(uri, workout, long.class);
        assertEquals(1, result);
    }

}