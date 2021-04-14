import React, { useState, useEffect, useContext } from 'react'
import './progressbar.scss'


export default function ProgressBar(props) {
    const { title, amount, total, classname, bgcolor } = props;
    const [ calAmount, setCalAmount ] = useState(0)
    let completed = Math.min( 100, (amount*100)/total ).toFixed(0)

    useEffect(() => {
        setCalAmount(amount)
    }, props)

    let fillerStyles = {
        width: `${completed}%`,
        backgroundImage: bgcolor,
        textAlign: 'right'
    }

    return (
        <div className={classname}>
            <div>{title}</div>
            <div className="bar-amount">{amount} kCal</div>
            <div className="container-style">
                <div style={fillerStyles} className="filler-style">
                <span className="label-style">{`${completed}%`}</span>
                </div>
            </div>
        </div>
    );
};

  