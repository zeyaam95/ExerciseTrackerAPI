import React, { useState, useEffect } from 'react'
import { BrowserRouter as Router, Route, Link } from 'react-router-dom'
import { Navbar, Nav, NavItem, NavDropdown, Button, FormControl } from 'react-bootstrap'
import logo from './assets/logo.png'
import Dashboard from './components/dashboard/Dashboard'
import Login from './components/login/login'
import './App.scss'
import { ThemeContext, Theme } from './contexts/ThemeContext'
import history from './history';

function App() {
  const [theme, setTheme] = useState(Theme.Dark)
  const value = { theme, setTheme }
  const [state, setState] = useState({
    userData: [],
    userProfile: {}
  })

  useEffect(() => {
    let title = "ExerciseTracker"
    if (state.userProfile.userName) title = `${state.userProfile.userName} - ${title}`
    document.title = title
  })

  const NaviBar = (props) => {
    console.log("BAR")
    return(
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
    )
  }

  return (
    <ThemeContext.Provider value={value}>
      <Router history={history}>
          <NaviBar/>
          <div className={theme + " Main"}>
            <Route path='/login' component={Login}/>
            <Route path='/dashboard' component={() => <Dashboard userProfile={state.userProfile} userData={state.userData}/>}/>
          </div>
          
      </Router>
    </ThemeContext.Provider>
  );
}

export default App;
