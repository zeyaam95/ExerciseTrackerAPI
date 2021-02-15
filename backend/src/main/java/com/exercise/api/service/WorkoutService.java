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

    public Workout getWorkoutById(long id) { return workoutRepository.findById(id).get(); }

    public List<Workout> getBetweenDates(Date from, Date to) {
        return workoutRepository.findByDateBetween(from, to);
    }

    public List<Workout> getWorkoutsByUserId(long id) { return workoutRepository.findWorkoutByUserId(id); }

    public List<Long> getWorkoutIdByUserId(long id) { return workoutRepository.findWorkoutIdByUserId(id); }

    public void saveOrUpdate(Workout workout) { workoutRepository.save(workout); }

    public void delete(long id) { workoutRepository.deleteById(id); }
}
