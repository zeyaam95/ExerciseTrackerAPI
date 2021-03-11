import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Link } from 'react-router-dom';
import { Navbar, Nav, NavItem, NavDropdown, Button, FormControl } from 'react-bootstrap';
import Form from 'react-bootstrap/Form'
import './App.scss';
import { ThemeContext, Theme } from './contexts/ThemeContext';

function App() {
  const [theme, setTheme] = useState(Theme.Light);
  const value = { theme, setTheme };

  useEffect(() => {
    document.title = "ExerciseTracker API"
  })

  return (
    <Router>
      <ThemeContext.Provider value={value}>
        <Route path='/'>
          <Navbar>
            <Navbar.Brand href='/'>ExerciseTracker</Navbar.Brand>
            <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="mr-auto"/>
            <Button 
              variant="flat"
              onClick={() => setTheme(theme === Theme.Dark? Theme.Light:Theme.Dark)}
              >
                {theme}
            </Button>
            <Form inline>
              <FormControl type="text" placeholder="Search" className="mr-sm-2" />
              <Button variant="outline-success">Search</Button>
            </Form>
          </Navbar.Collapse>
          </Navbar>
          <div className={theme + " Main"}>
              
              <div className="Test">wow</div>
          </div>
        </Route>
      </ThemeContext.Provider>
    </Router>
  );
}

export default App;
