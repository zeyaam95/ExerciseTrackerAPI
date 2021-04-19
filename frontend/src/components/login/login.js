import React, { useState, useEffect, useContext, useCallback } from 'react'
import _ from 'lodash'
import * as utils from '../../utils/utils'
import './login.scss';
import axios from 'axios';
import history from '../../history';
import { Form, Nav, NavItem, NavDropdown, Button, FormControl } from 'react-bootstrap'

export default function Login(props) {
    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");
    const [weight, setWeight] = useState(0);
    const [height, setHeight] = useState(0);
    const [dob, setDOB] = useState();
    const [appState, setAppState] = useState(0);

    let handleOnClick = () => {
        let URL = utils.USER_PROFILE_API 
        console.log("before login",userName, URL)
        if (!appState) {
            URL += `name/${localStorage.getItem("user")}`
            console.log("before login",userName, URL)
            axios.post(URL.toString(), password)
            .then((response)=>{
                localStorage.setItem("userId", response.data)
                console.log("\n\n\n", "LOGGED IN",localStorage, response.request.status)
                history.push('/dashboard')
                window.location.reload(false);
            })
            .catch(error => console.log(error))
        }
        else {
            let user = {
                "userName": userName,
                "password": password,
                "joinDate": utils.formatDate(new Date()).replaceAll('-', '/'),
                "dob": dob.replaceAll('-', '/'),
                "weight": weight,
                "height": height,
            }
            axios.post(utils.USER_PROFILE_API.toString(), user)
            .then((response)=>{
                localStorage.setItem("userId", response.data)
                alert("Sign up successful!")
                setAppState(0)
                setWeight(0)
                setHeight(0)
                setDOB('')
            })
            .catch(error => console.log(error))
        }
        
    }

    useEffect(() =>{
        localStorage.setItem("user", userName)
        console.log("LOCAL STORAGE: ",userName, localStorage)
    }, [userName])

    let loginClass = (appState)? "form-button-login" : "form-button-login selectedButton"
    let signupClass = (appState)? "form-button-signup selectedButton" : "form-button-signup"

    return (
        <div className="login-main">
            <div className="form-button-wrapper">
                    <div 
                        className={loginClass} 
                        onClick={()=>{if (appState) setAppState(0)}}>
                        <div className="form-button-padding">
                            Login
                        </div>
                    </div>
                    <div 
                        className={signupClass} 
                        onClick={()=>{if (!appState) setAppState(1)}}>
                        <div className="form-button-padding">
                            Sign Up
                        </div>
                    </div>
                </div>
            <div className="form-wrapper">
                
                <Form onSubmit={(e)=>{
                        e.preventDefault()
                        handleOnClick()
                    }}>
                <Form.Group controlId="formBasicUserName">
                    <Form.Label>Username</Form.Label>
                    <Form.Control 
                        type="text" 
                        placeholder="Enter username" 
                        value={userName}
                        onChange={(e)=>{
                            e.preventDefault()
                            setUserName(e.target.value)
                        }}
                        required 
                    />
                </Form.Group>

                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Password</Form.Label>
                    <Form.Control 
                        type="password" 
                        placeholder="Password" 
                        value={password}
                        onChange={(e)=>{
                            e.preventDefault()
                            setPassword(e.target.value)
                        }}
                        required 
                    />
                </Form.Group>

                {(appState)?
                <>
                <Form.Group controlId="formBasicWeight">
                    <Form.Label>Weight in (kg)</Form.Label>
                    <Form.Control 
                        type="number" 
                        placeholder="Weight in (kg)" 
                        value={weight}
                        onChange={(e)=>{
                            const re = /^[0-9\b]+$/;
                            if (e.target.value === '' || re.test(e.target.value)) {
                               setWeight(Math.abs(parseInt(e.target.value)))
                            }
                        }} 
                    />
                </Form.Group>

                <Form.Group controlId="formBasicHeight">
                    <Form.Label>Height in (cm)</Form.Label>
                    <Form.Control 
                        type="number" 
                        placeholder="Height in (cm)" 
                        value={height}
                        onChange={(e)=>{
                            const re = /^[0-9\b]+$/
                            if (e.target.value === '' || re.test(e.target.value)) {
                               setHeight(Math.abs(parseInt(e.target.value)))
                            }
                        }} 
                    />
                </Form.Group>

                <Form.Group controlId="formBasicDOB">
                    <Form.Label>Username</Form.Label>
                    <Form.Control 
                        type="text" 
                        placeholder="Enter Date of Birth (YYYY-MM-DD)" 
                        value={dob}
                        onChange={(e)=>{
                            const re = /^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$/
                            if (e.target.value === '' || re.test(e.target.value)) {
                                setDOB(e.target.value)
                            }
                        }}
                        required 
                    />
                </Form.Group>
                </>
                :
                <></>
                
                }

                <Button 
                    variant="primary" 
                    type="submit"
                >
                    {(appState)?"Sign up":"Login"}
                </Button>
                </Form>
            </div>
        </div>
    )
}