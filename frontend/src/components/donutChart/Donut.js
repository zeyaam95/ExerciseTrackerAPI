import React, { useState, useEffect } from "react";
import { Doughnut } from "react-chartjs-2";

export default function Donut(props) {
    const [ state, setState ] = useState({
        dataDoughnut: {
            labels: ["Strength", "Cardio"],
            datasets: [
                {
                    data: [props.strength, props.cardio],
                    backgroundColor: ["#9b59b6", "#f1c40f"],
                    hoverBackgroundColor: ["#9b59b6", "#f1c40f"]
                }
            ]
        }
    })

    const options= {
        legend: {
           display: false
        },
        elements: {
            arc: {
              borderWidth: 0
            }
        },
        responsive: true,
        maintainAspectRatio: true
   }

    return (
        <div className="donut-wrapper">
            <Doughnut 
                data={state.dataDoughnut} 
                options={options}
                width= {"50%"}
            />
        </div>
    )
    
}
