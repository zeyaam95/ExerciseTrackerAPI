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
    public Exercise getExercise(@PathVariable("id") long id) throws Exception {
        return exerciseService.getExerciseById(id);
    }

    @GetMapping("workout/{workoutId}")
    public List<Exercise> getExerciseByWorkout(@PathVariable("workoutId") long workoutId) throws Exception {
        return exerciseService.getExerciseByWorkoutId(workoutId);
    }

    @GetMapping("workout/{workoutId}/{type}")
    public List<Exercise> getExerciseByWorkout(
            @PathVariable("workoutId") long workoutId,
            @PathVariable("type") String type) throws Exception {
        return exerciseService.getExerciseByWorkoutIdAndType(workoutId, type);
    }

    @GetMapping("user/{userId}")
    public List<Exercise> getAllExercisesByUserId(@PathVariable("userId") long userId) throws Exception {
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
            @PathVariable("type") String type) throws Exception {
        List<Exercise> allExercises = new ArrayList<>();
        List<Workout> userWorkouts = workoutService.getWorkoutsByUserId(userId);
        for (Workout workout : userWorkouts) {
            allExercises.addAll(exerciseService.getExerciseByWorkoutIdAndType(workout.getWorkoutId(), type));
        }
        return allExercises;
    }

    @DeleteMapping("{id}")
    public void deleteExercise(@PathVariable("id") long id) throws Exception {
        exerciseService.delete(id);
    }

    @PostMapping()
    public long saveExercise(@RequestBody Exercise exercise) throws Exception {
        exerciseService.saveOrUpdate(exercise);
        return exercise.getExerciseId();
    }
}
