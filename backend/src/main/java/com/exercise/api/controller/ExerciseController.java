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
@RequestMapping("/exercise")
public class ExerciseController {
    @Autowired
    ExerciseService exerciseService;

    @Autowired
    WorkoutService workoutService;

    @GetMapping()
    public List<Exercise> getAllExercises() {
        return exerciseService.getAllExercises();
    }

    @GetMapping("{id}")
    public Exercise getExercise(@PathVariable("id") long id) {
        return exerciseService.getExerciseById(id);
    }

    @GetMapping("workout/{workoutId}")
    public List<Exercise> getExerciseByWorkout(@PathVariable("workoutId") long workoutId) {
        return exerciseService.getExerciseByWorkoutId(workoutId);
    }

    @GetMapping("workout/{workoutId}/{type}")
    public List<Exercise> getExerciseByWorkout(
            @PathVariable("workoutId") long workoutId,
            @PathVariable("type") String type) {
        return exerciseService.getExerciseByWorkoutIdAndType(workoutId, type);
    }

    @GetMapping("user/{userId}")
    public List<Exercise> getAllExercisesByUserId(@PathVariable("userId") long userId) {
        List<Exercise> allExercises = new ArrayList<>();
        List<Workout> userWorkouts = workoutService.getWorkoutsByUserId(userId);
        for (Workout workout : userWorkouts) {
            allExercises.addAll(exerciseService.getExerciseByWorkoutId(workout.getWorkoutId()));
        }
        return allExercises;
    }

    @GetMapping("user/{userId}/{type}")
    public List<Exercise> getAllExercisesByUserId(
            @PathVariable("userId") long userId,
            @PathVariable("type") String type) {
        List<Exercise> allExercises = new ArrayList<>();
        List<Workout> userWorkouts = workoutService.getWorkoutsByUserId(userId);
        for (Workout workout : userWorkouts) {
            allExercises.addAll(exerciseService.getExerciseByWorkoutIdAndType(workout.getWorkoutId(), type));
        }
        return allExercises;
    }

    @DeleteMapping("{id}")
    public void deleteExercise(@PathVariable("id") long id) {
        exerciseService.delete(id);
    }

    @PostMapping()
    public long saveExercise(@RequestBody Exercise exercise) {
        exerciseService.saveOrUpdate(exercise);
        return exercise.getExerciseId();
    }
}
