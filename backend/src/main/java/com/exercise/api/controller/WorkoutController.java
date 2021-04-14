package com.exercise.api.controller;

import com.exercise.api.model.Workout;
import com.exercise.api.service.WorkoutService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/workout")
@CrossOrigin(origins = "http://localhost:9000")
public class WorkoutController {

    @Autowired
    WorkoutService workoutService;

    @GetMapping()
    private List<Workout> getAllWorkouts() {
        return workoutService.getAllWorkouts();
    }

    @GetMapping("{id}")
    private Workout getWorkout(@PathVariable("id") long id) throws Exception {
        return workoutService.getWorkoutById(id);
    }

    @GetMapping("dates/{from}/{to}")
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT-05:00")
    private List<Workout> getBetweenDates(@PathVariable("from") Date from, @PathVariable("to") Date to) throws Exception {
        return workoutService.getBetweenDates(from, to);
    }

    @GetMapping("user/{userId}")
    private List<Workout> getWorkoutsByUserId(@PathVariable("userId") long userId) throws Exception {
        return workoutService.getWorkoutsByUserId(userId);
    }

    @GetMapping("user/{userId}/idonly")
    public List<Long> getWorkoutIdsByUserId(@PathVariable("userId") long userId) throws Exception {
        return workoutService.getWorkoutIdByUserId(userId);
    }

    @DeleteMapping("{id}")
    private void deleteWorkout(@PathVariable("id") long id) throws Exception {
        workoutService.delete(id);
    }

    @PostMapping("")
    private long saveWorkout(@RequestBody Workout workout) throws Exception {
        workoutService.saveOrUpdate(workout);
        return workout.getWorkoutId();
    }
}
