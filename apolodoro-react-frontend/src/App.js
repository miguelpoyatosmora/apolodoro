import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import LoginPage from './containers/LoginPage';
import SignUpPage from './containers/SignUpPage';


class App extends Component {
  render() {
    return (
    <MuiThemeProvider muiTheme={getMuiTheme()}>
      <div className="App">
        <div className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
        </div>
        <LoginPage />
      </div>
      </MuiThemeProvider>
    );
  }
}

export default App;
