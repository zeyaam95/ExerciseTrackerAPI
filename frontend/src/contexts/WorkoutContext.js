import React from "react";

export const WorkoutContext = React.createContext({
    workout: {},
    setWorkout: (workout) => console.warn('no workout provider')
})