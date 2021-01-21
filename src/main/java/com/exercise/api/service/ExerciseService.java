package com.exercise.api.service;

import com.exercise.api.model.Exercise;
import com.exercise.api.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExerciseService {
    @Autowired
    ExerciseRepository exerciseRepository;

    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<>();
        exerciseRepository.findAll().forEach(exercise -> exercises.add(exercise));
        return exercises;
    }

    public Exercise getExerciseById(long id) {
        return exerciseRepository.findById(id).get();
    }

    public void saveOrUpdate(Exercise exercise) {
        exerciseRepository.save(exercise);
    }

    public void delete(long id) {
        exerciseRepository.deleteById(id);
    }

    public List<Exercise> getExerciseByWorkoutId(long workoutId) {
        return exerciseRepository.findExerciseByWorkoutId(workoutId);
    }

    public List<Exercise> getExerciseByWorkoutIdAndType(long workoutId, String type) {
        return exerciseRepository.findExerciseByWorkoutIdAndType(workoutId, type);
    }
}
