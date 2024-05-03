import axios from 'axios';

// Retrieve token from localStorage
const token = localStorage.getItem('token');

const apiService = axios.create({
  baseURL: '',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}` // Set token as authorization header
  }
});

export default apiService;