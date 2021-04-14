package com.exercise.api.service;

import com.exercise.api.model.Workout;
import com.exercise.api.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WorkoutService {

    @Autowired
    WorkoutRepository workoutRepository;

    public List<Workout> getAllWorkouts() {
        List<Workout> workouts = new ArrayList<>();
        workoutRepository.findAll().forEach(workout -> workouts.add(workout));
        return workouts;
    }

    public Workout getWorkoutById(long id) throws Exception {
        if (id < 0) {
            throw new Exception("workoutId cannot be negative");
        }
        return workoutRepository.findById(id).get();
    }

    public List<Workout> getBetweenDates(Date from, Date to) throws Exception {
        if (from.after(to)) {
            throw new Exception("Date range is invalid: Start date cannot be after End date");
        }
        return workoutRepository.findByDateBetween(from, to);
    }

    public List<Workout> getWorkoutsByUserId(long id) throws Exception {
        if (id < 0) {
            throw new Exception("workoutId cannot be negative");
        }
        return workoutRepository.findWorkoutByUserId(id);
    }

    public List<Long> getWorkoutIdByUserId(long id)  throws Exception {
        if (id < 0) {
            throw new Exception("workoutId cannot be negative");
        }
        return workoutRepository.findWorkoutIdByUserId(id);
    }

    public void saveOrUpdate(Workout workout) throws Exception {
        if (workout.getWorkoutId() < 0) {
            throw new Exception("workoutId cannot be negative");
        }
        workoutRepository.save(workout);
    }

    public void delete(long id) throws Exception {
        if (id < 0) {
            throw new Exception("workoutId cannot be negative");
        }
        workoutRepository.deleteById(id);
    }
}
