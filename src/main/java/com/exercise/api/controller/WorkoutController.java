package com.exercise.api.controller;

import com.exercise.api.model.Workout;
import com.exercise.api.service.WorkoutService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class WorkoutController {
    @Autowired
    WorkoutService workoutService;

    @GetMapping("/workout")
    private List<Workout> getAllWorkouts() {
        return workoutService.getAllWorkouts();
    }

    @GetMapping("/workout/{id}")
    private Workout getWorkout(@PathVariable("id") long id) {
        return workoutService.getWorkoutById(id);
    }

    @GetMapping("/workout/dates/{from}/{to}")
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT-05:00")
    private List<Workout> getBetweenDates(@PathVariable("from") Date from, @PathVariable("to") Date to) {
        return workoutService.getBetweenDates(from, to);
    }

    @GetMapping("/workout/user/{userId}")
    private List<Workout> getWorkoutsByUserId(@PathVariable("userId") long userId) {
        return workoutService.getWorkoutsByUserId(userId);
    }

    @GetMapping("/workout/user/{userId}/idonly")
    private List<Long> getWorkoutIdsByUserId(@PathVariable("userId") long userId) {
        return workoutService.getWorkoutIdByUserId(userId);
    }

    @DeleteMapping("/workout/{id}")
    private void deleteWorkout(@PathVariable("id") long id) {
        workoutService.delete(id);
    }

    @PostMapping("/workout")
    private long saveWorkout(@RequestBody Workout workout) {
        workoutService.saveOrUpdate(workout);
        return workout.getWorkoutId();
    }
}
