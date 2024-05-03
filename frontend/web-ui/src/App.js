import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.css';
import LoginPage from './loginpage';
import Login from './login';
import Customer from './customer-info-form'; // Changed import statement
import { useEffect, useState } from 'react';

function App() {
  const [loggedIn, setLoggedIn] = useState(false);
  const [userName, setUserName] = useState('');

  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<LoginPage username={userName} loggedIn={loggedIn} setLoggedIn={setLoggedIn} />} />
          <Route path="/login" element={<Login setLoggedIn={setLoggedIn} setEmail={setUserName} />} />
          <Route path="/customer-info-form" element={<Customer />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;