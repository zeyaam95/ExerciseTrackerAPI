export const API = 'http://localhost:9000/'
export const USER_DATA_API = `${API}workout/user/1`
export const USER_PROFILE_API = `${API}user/1`

export const USER_PROFILE_DEFAULT = {
    "User Name" : "",
    "Weight": "",
    "Height": "",
    "Age": "",
    "Date Joined": ""
}

export const WORKOUT_STATS_DEFAULT = {
    "Strength": 0,
    "Cardio": 0,
    "Calories": 0
}

export function toFeet(n) {
    var realFeet = ((n*0.393700) / 12);
    var feet = Math.floor(realFeet);
    var inches = Math.round((realFeet - feet) * 12);
    return `${feet} feet ${inches} inches`;
}

export function convertDate(date) {
    var days = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']
    var months = [ "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" ]
    var jsDate = new Date(date)
    var day = days[jsDate.getDay()]
    var date = jsDate.getDate()
    var month = months[jsDate.getMonth()]
    var year = jsDate.getFullYear()
    return `${day}, ${month} ${date}, ${year}`
}

function _getWorkoutStats(workoutData, exType) {
    return workoutData.reduce(
    (totalCalories, workout) => {
        let cals = workout.exercises.reduce((total, exercise)=>{return (exercise.type === exType)? total + exercise.calories : total}, 0)
        return totalCalories + cals
    }, 0)
}

export function getWorkoutStats(workoutData) {
    let cardioCalories = _getWorkoutStats(workoutData, "Cardio")
    let strengthCalories = _getWorkoutStats(workoutData, "Strength")
    return {
        "Strength": strengthCalories,
        "Cardio": cardioCalories,
        "Calories": strengthCalories + cardioCalories
    }
}