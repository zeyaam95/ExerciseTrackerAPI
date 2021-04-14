import React, { useState, useEffect } from 'react'
import { BrowserRouter as Router, Route, Link } from 'react-router-dom'
import { Navbar, Nav, NavItem, NavDropdown, Button, FormControl } from 'react-bootstrap'
import logo from './assets/logo.png'
import Form from 'react-bootstrap/Form'
import Dashboard from './components/dashboard/Dashboard'
import './App.scss'
import { ThemeContext, Theme } from './contexts/ThemeContext'
import { API, USER_DATA_API, USER_PROFILE_API } from './utils/utils'
import axios from 'axios'

function App() {
  const [theme, setTheme] = useState(Theme.Dark)
  const value = { theme, setTheme }
  const [state, setState] = useState({
    userData: [],
    userProfile: {}
  })

  async function fetchData() {
    axios.all([
      axios.get(USER_DATA_API),
      axios.get(USER_PROFILE_API)
    ])
    .then(axios.spread((userData, userProfile) => {
      console.log("USER DATA: ", userData)
      setState({
        ...state,
        userData: userData.data,
        userProfile: userProfile.data
      })
      document.title = userProfile.data.userName
      console.log(userProfile.data)
    })).catch(error => console.log(error))
  }

  useEffect(() => {
    let title = "ExerciseTracker"
    if (state.userProfile.userName) title = `${state.userProfile.userName} - ${title}`
    document.title = title
  })

  useEffect( () => {
    fetchData()
  }, [])

  return (
    <Router>
      <ThemeContext.Provider value={value}>
        <Route path='/'>
          <Navbar className="color-nav">
            <Navbar.Brand href='/' className="navbar-brand">
              <img src={logo} width="100px"/> {' '}
              ExerciseTracker
            </Navbar.Brand>
            <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="mr-auto"/>
            <Button 
              variant="flat"
              onClick={() => setTheme(theme === Theme.Dark? Theme.Light:Theme.Dark)}
              >
                {`${theme} Mode`}
            </Button>
            
          </Navbar.Collapse>
          </Navbar>
          <div className={theme + " Main"}>
              <Dashboard userProfile={state.userProfile} userData={state.userData}/>
          </div>
        </Route>
      </ThemeContext.Provider>
    </Router>
  );
}

export default App;
