import React, { useState } from 'react';
import apiService from './apiService';

const Customer = () => {
  const [customer, setCustomer] = useState({
    firstname: '',
    name: '',
    placeOfBirth: '',
    dateOfBirth: '',
    nationality: '',
    sex: '',
    cin: '',
    email: '',
    phone: ''
  });

  const handleChange = (event) => {
    const { name, value } = event.target;
    setCustomer({ ...customer, [name]: value });
  };

  const token = localStorage.getItem('token');
  const onButtonClick = () => {
    
    console.log('Button clicked');
};

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await apiService.post('http://localhost:8887/bank/v2/customers/save', customer);
      console.log('Form submitted successfully:', response.data);
      // Handle form submission success
    } catch (error) {
      console.error('Form submission error:', error);
      // Handle form submission error
    }
  };

  return (
    <div>
      <h2>Form Page</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="firstname">First Name:</label>
          <input
            type="text"
            id="firstname"
            name="firstname"
            value={customer.firstname}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label htmlFor="name">Name:</label>
          <input
            type="text"
            id="name"
            name="name"
            value={customer.name}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label htmlFor="placeOfBirth">Place of Birth:</label>
          <input
            type="text"
            id="placeOfBirth"
            name="placeOfBirth"
            value={customer.placeOfBirth}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label htmlFor="dateOfBirth">Date of Birth:</label>
          <input
            type="date"
            id="dateOfBirth"
            name="dateOfBirth"
            value={customer.dateOfBirth}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label htmlFor="nationality">Nationality:</label>
          <input
            type="text"
            id="nationality"
            name="nationality"
            value={customer.nationality}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label htmlFor="sex">Sex:</label>
          <input
            type="text"
            id="sex"
            name="sex"
            value={customer.sex}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label htmlFor="cin">CIN:</label>
          <input
            type="text"
            id="cin"
            name="cin"
            value={customer.cin}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label htmlFor="email">Email:</label>
          <input
            type="email"
            id="email"
            name="email"
            value={customer.email}
            onChange={handleChange}
            required
          />
        </div>
        <div>
          <label htmlFor="phone">Phone:</label>
          <input
            type="tel"
            id="phone"
            name="phone"
            value={customer.phone}
            onChange={handleChange}
            required
          />
        </div>
        <button type="submit" >Submit</button>
      </form>

      <button type ="button" onClick={onButtonClick}>Display Token</button>
      {token && <div>Token: {token}</div>}
    </div>
  );
};

export default Customer;