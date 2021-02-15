package com.exercise.api.repository;

import com.exercise.api.model.Exercise;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExerciseRepository extends CrudRepository<Exercise, Long> {
    @Query("SELECT exercise FROM Workout workout JOIN workout.exercises exercise WHERE workout.workoutId = :workoutId")
    List<Exercise> findExerciseByWorkoutId(@Param("workoutId") long workoutId);

    @Query("SELECT exercise FROM Workout workout JOIN workout.exercises exercise WHERE workout.workoutId = :workoutId AND exercise.type = :exerciseType")
    List<Exercise> findExerciseByWorkoutIdAndType(@Param("workoutId") long workoutId, @Param("exerciseType") String exerciseType);
}

