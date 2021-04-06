import React, { useState, useEffect } from 'react'
import ProgressBar from '../progressBar/ProgressBar'
import Workout from '../workout/Workout'
import * as utils from '../../utils/utils'
import './dashboard.scss';
import profilePic from '../../assets/larry.jpg'
import { WorkoutContext } from '../../contexts/WorkoutContext'
import axios from 'axios'

export default function Dashboard(props) {
    const [userProfile, setProfile] = useState(utils.USER_PROFILE_DEFAULT)
    const [workoutStats, setWorkoutStats] = useState(utils.WORKOUT_STATS_DEFAULT)
    const [selectedWorkoutIndex, setSelectedWorkoutIndex] = useState()
    const [workoutData, setWorkoutData] = useState([])
    const [workout, setWorkout] = useState({})
    let workoutContextValue = { workout, setWorkout }

    useEffect(()=>{
        let weight = `${props.userProfile.weight} kg | ${(props.userProfile.weight*2.20462).toFixed(0)} lbs`
        let height = `${props.userProfile.height} cm | ${utils.toFeet(props.userProfile.height)}`

        let newProfile = {
            "User Name" : props.userProfile.userName,
            "Weight": weight,
            "Height": height,
            "Age": `${props.userProfile.age} years old`,
            "Member Since": utils.convertDate(props.userProfile.joinDate)
        }
        if (props.userData.length) {
            //console.log("LOOP: ", props.userData)
            setWorkoutStats(utils.getWorkoutStats(props.userData))
        }
        setProfile(()=>newProfile)
        setWorkoutData(props.userData)
        //console.log("Profile Set", workoutData.length, props.userData, props.userData.length, workoutStats, utils.getWorkoutStats(workoutData))
        
        return newProfile
    }, [props])


    //console.log("RUNNING",props, workoutContextValue)
    return (
        <div className="dashboard">
            <div className="user-info">
                <div className="profile-pic-wrapper">
                    <img src={profilePic} className="profile-pic"/>
                </div>
                <div className="profile-data-wrapper">
                    {Object.keys(userProfile).map((key) => {
                        return (
                        <div className="profile-data-row">
                            <div className="profile-key">{key}</div>
                            <div className="profile-value">{userProfile[key]}</div>
                        </div>)
                    })}
                </div>
            </div>
            <WorkoutContext.Provider value={workoutContextValue}>
                <div className="user-data">
                    <div className="workout-list-wrapper">
                        <div className="workout-list-header">
                            Workouts
                        </div>
                        <div className="workout-list-add">
                            +
                        </div>
                        <div 
                            className="workout-list-delete"
                            onClick={
                                () => {
                                    if (Number.isInteger(selectedWorkoutIndex)) {
                                        let workoutId = workoutData[selectedWorkoutIndex].workoutId
                                        axios.delete(`${utils.API}workout/${workoutId}`)
                                            .then(response => {
                                                console.log("DELETED: ", response)
                                        })
                                        workoutData.splice(selectedWorkoutIndex, 1)
                                        setSelectedWorkoutIndex("")
                                        setWorkoutData(workoutData)
                                        setWorkout({})
                                        workoutContextValue = { workout, setWorkout }
                                        setWorkoutStats(utils.getWorkoutStats(workoutData))
                                    }
                                }
                            }
                            >
                            -
                        </div>
                        <div className="workout-list-items">
                            {
                            workoutData
                            .sort((a,b) => (a.date > b.date) ? 1 : ((b.date > a.date) ? -1 : 0))
                            .map((item, index) => {
                                let className = "workout-list-entry"
                                if (index === selectedWorkoutIndex) {
                                    className += " workout-list-entry-selected shadow"
                                }
                                return (
                                    <div 
                                        className={className}
                                        onClick={()=>{
                                            setWorkout(item)
                                            workoutContextValue = { workout, setWorkout }
                                            setSelectedWorkoutIndex(index)
                                            }}>
                                        {utils.convertDate(item.date)}
                                    </div>
                                )
                            })
                            }
                        </div>
                    </div>
                    <div className="workout-summary-wrapper">
                        <ProgressBar 
                            title="Strength" 
                            amount={workoutStats.Strength}
                            total={5500}
                            classname="workout-summary-strength" 
                            bgcolor={"linear-gradient(to right, #474787, #8e44ad 55%)"}
                        />
                        <ProgressBar 
                            title="Cardio" 
                            amount={workoutStats.Cardio}
                            total={1700}
                            classname="workout-summary-cardio" 
                            bgcolor={"linear-gradient(to right, #e74c3c, #f39c12 55%)"}
                        />
                        <ProgressBar 
                            title="Total Calories" 
                            amount={workoutStats.Calories}
                            total={7200}
                            classname="workout-summary-calories" 
                            bgcolor={"linear-gradient(to right, #006266, #2ecc71 65%)"}
                        />
                        <Workout 
                            className="workout-summary"
                            workoutStats={workoutStats}
                        />
                    </div>
                </div>
            </WorkoutContext.Provider>
        </div>
    )
}