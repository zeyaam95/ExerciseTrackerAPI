package com.exercise.api.controller;

import com.exercise.api.model.Exercise;
import com.exercise.api.model.Workout;
import com.exercise.api.service.ExerciseService;
import com.exercise.api.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ExerciseController {
    @Autowired
    ExerciseService exerciseService;

    @Autowired
    WorkoutService workoutService;

    @GetMapping("/exercise")
    private List<Exercise> getAllExercises() {
        return exerciseService.getAllExercises();
    }

    @GetMapping("/exercise/{id}")
    private Exercise getExercise(@PathVariable("id") long id) {
        return exerciseService.getExerciseById(id);
    }

    @GetMapping("/exercise/workout/{workoutId}")
    private List<Exercise> getExerciseByWorkout(@PathVariable("workoutId") long workoutId) {
        return exerciseService.getExerciseByWorkoutId(workoutId);
    }

    @GetMapping("/exercise/workout/{workoutId}/{type}")
    private List<Exercise> getExerciseByWorkout(
            @PathVariable("workoutId") long workoutId,
            @PathVariable("type") String type) {
        return exerciseService.getExerciseByWorkoutIdAndType(workoutId, type);
    }

    @GetMapping("/exercise/user/{userId}")
    private List<Exercise> getAllExercisesByUserId(@PathVariable("userId") long userId) {
        List<Exercise> allExercises = new ArrayList<>();
        List<Workout> userWorkouts = workoutService.getWorkoutsByUserId(userId);
        for (Workout workout : userWorkouts) {
            allExercises.addAll(exerciseService.getExerciseByWorkoutId(workout.getWorkoutId()));
        }
        return allExercises;
    }

    @GetMapping("/exercise/user/{userId}/{type}")
    private List<Exercise> getAllExercisesByUserId(
            @PathVariable("userId") long userId,
            @PathVariable("type") String type) {
        List<Exercise> allExercises = new ArrayList<>();
        List<Workout> userWorkouts = workoutService.getWorkoutsByUserId(userId);
        for (Workout workout : userWorkouts) {
            allExercises.addAll(exerciseService.getExerciseByWorkoutIdAndType(workout.getWorkoutId(), type));
        }
        return allExercises;
    }

    @DeleteMapping("/exercise/{id}")
    private void deleteExercise(@PathVariable("id") long id) {
        exerciseService.delete(id);
    }

    @PostMapping("/exercise")
    private long saveExercise(@RequestBody Exercise exercise) {
        exerciseService.saveOrUpdate(exercise);
        return exercise.getExerciseId();
    }
}
