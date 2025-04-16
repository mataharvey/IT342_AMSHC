import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function Register() {
  const navigate = useNavigate(); // Hook for navigation
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState(''); // Error message for validation
  const [loading, setLoading] = useState(false); // Loading state to show a loading indicator

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validate password and confirm password match
    if (password !== confirmPassword) {
      setError('Passwords do not match!');
      return;
    }

    // Show loading indicator while waiting for the API call
    setLoading(true);

    try {
      // Make an API call to register the user
      const response = await axios.post('http://localhost:8080/api/auth/register', {
        email: email,
        password: password,
      });

      // Handle successful registration
      console.log('User registered successfully:', response.data);

      // After successful registration, redirect to the login page
      navigate('/login'); // Navigate to the login page
    } catch (err) {
      // Handle errors from the API (e.g., username already taken or server error)
      setError('An error occurred while registering.');
      console.error('Registration error:', err);
    } finally {
      // Hide the loading indicator after the API call finishes
      setLoading(false);
    }
  };

  return (
    <div className="flex justify-center items-center h-screen bg-blue-800">
      <div className="bg-white p-8 rounded-lg shadow-lg w-96">
        <h2 className="text-center text-3xl text-blue-800 font-semibold mb-6">Register</h2>
        {error && <div className="text-red-600 mb-4">{error}</div>} {/* Error display */}

        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label htmlFor="email" className="block text-sm font-medium text-blue-800">Email</label>
            <input
              type="email"
              id="email"
              className="w-full px-4 py-2 mt-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className="mb-4">
            <label htmlFor="password" className="block text-sm font-medium text-blue-800">Password</label>
            <input
              type="password"
              id="password"
              className="w-full px-4 py-2 mt-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <div className="mb-6">
            <label htmlFor="confirmPassword" className="block text-sm font-medium text-blue-800">Confirm Password</label>
            <input
              type="password"
              id="confirmPassword"
              className="w-full px-4 py-2 mt-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              required
            />
          </div>
          <button
            type="submit"
            className="w-full py-2 bg-blue-800 text-white font-semibold rounded-md hover:bg-blue-700 transition"
            disabled={loading}
          >
            {loading ? 'Registering...' : 'Create Account'}
          </button>
        </form>

        <div className="text-center mt-4">
          <p className="text-sm text-blue-800">
            Already have an account?
            <a href="/login" className="text-blue-500 hover:underline">Login</a>
          </p>
        </div>
      </div>
    </div>
  );
}

export default Register;
