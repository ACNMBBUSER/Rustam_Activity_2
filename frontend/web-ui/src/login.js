import React, { useState } from 'react'
import apiService from './apiService';
import { useNavigate } from 'react-router-dom';

const Login = () => {
  const [username, setUserName] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [token, setToken] = useState('');
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const response = await apiService.post('http://localhost:8885/bank/login', { username, password });
      // Handle successful login response
      console.log('Login successful:', response.data);
      setToken(response.data.token);
      const key = response.data.token;
      localStorage.setItem('token', key);
      console.log(localStorage.getItem('token'));

      navigate('/customer-info-form');

    } catch (error) {
      // Handle error response
      console.error('Login error:', error);
      setError('An unexpected error occurred. Please try again later.');
    }
  };

  return (
    <div className={'mainContainer'}>
      <div className={'titleContainer'}>
        <div>Login</div>
      </div>
      <br />
      <div className={'inputContainer'}>
        <input
          value={username}
          placeholder="username"
          onChange={(ev) => setUserName(ev.target.value)}
          className={'inputBox'}
        />
      </div>
      <br />
      <div className={'inputContainer'}>
        <input
          value={password}
          placeholder="Enter your password here"
          type="password"
          onChange={(ev) => setPassword(ev.target.value)}
          className={'inputBox'}
        />
      </div>
      <br />
      <div className={'inputContainer'}>
        <input className={'inputButton'} type="button" onClick={handleLogin} value={'Log in'} />
      </div>
      {token && <div>Token: {token}</div>}
      {error && <div className="errorLabel">{error}</div>}
    </div>
  );
};

export default Login;