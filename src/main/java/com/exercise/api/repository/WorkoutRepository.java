package com.exercise.api.repository;

import com.exercise.api.model.Workout;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface WorkoutRepository extends CrudRepository<Workout, Long> {
    List<Workout> findByDateBetween(Date from, Date to);

    @Query("SELECT workout FROM Workout workout where workout.userId = :userId")
    List<Workout> findWorkoutByUserId(@Param("userId") long userId);

    @Query("SELECT workout.workoutId FROM Workout workout where workout.userId = :userId")
    List<Long> findWorkoutIdByUserId(@Param("userId") long userId);

}
