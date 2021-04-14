import React, { useState, useEffect, useContext } from 'react'
import Donut from '../donutChart/Donut'
import _ from 'lodash'
import * as utils from '../../utils/utils'
import './workout.scss';
import { WorkoutContext } from '../../contexts/WorkoutContext'
import axios from 'axios';

export default function Workout(props) {
    const [ workoutStats, setWorkoutStats] = useState(utils.WORKOUT_STATS_DEFAULT)
    const workout = useContext(WorkoutContext)

    useEffect(() => {
        setWorkoutStats(props.workoutStats)
    }, [WorkoutContext.Consumer, props])

    let ExerciseTemplate = (props) => {
        let exercises = workout.workout.exercises.map( (exercise) => {
            let pluralCheck = (item) => (item > 1)? "s":""
            let typeClass = (exercise.type === "Strength")? {color: '#9b59b6'} : {color: '#f39c12'}
            return (
                <div className="exercise-list-row">
                    <div>{exercise.name}</div>
                    <div style={typeClass}>{exercise.type}</div>
                    <div>{`${exercise.duration} minutes`}</div>
                    <div>{`${exercise.calories} kCal`}</div>
                    <div>{exercise.notes}</div>
                    <div>{`${exercise.sets} set${pluralCheck(exercise.sets)}`}</div>
                    <div>{`${exercise.reps} rep${pluralCheck(exercise.reps)}`}</div>
                </div>
            )
        })
        return (
            <div>
                {exercises}
            </div>
        )
    }
    
    let WorkoutTemplate = (props) => {
        let properties = {}
        let workoutStats = {}
        if (workout.workout.exercises !== undefined) {
            properties = {..._.pick(workout.workout,['totalDuration', 'date', 'numExercises']), ...utils.getWorkoutStats([workout.workout])}
            workoutStats = {
                "Date": properties.date,
                "Strength Calories": properties.Strength,
                "Total Duration": properties.totalDuration,
                "Cardio Calories": properties.Cardio,
                "Number of Exercises": properties.numExercises,
                "Total Calories": properties.Calories
            }
        }
        
        return (
            <div className="workout-grid">
                <div className="workout-header">
                    Workout Summary
                </div>
                <div className="workout-stats">
                    {Object.keys(workoutStats).map((key, index) => {
                        return (<div className="workout-stats-slot">
                            <div className="workout-stats-key">{key}</div>
                            <div className="workout-stats-value">{workoutStats[key]}</div>
                    </div>)})}
                </div>
                <div className="workout-exercise">
                    <div className="workout-exercise-list">
                        <ExerciseTemplate/>
                    </div>
                </div>
            </div>
        )
    }
    
    return (
        <div className="workout-summary">
        { (workout.workout.exercises !== undefined)? 
             <WorkoutTemplate/>
             :
            " "
        }
        </div>
    )
}
