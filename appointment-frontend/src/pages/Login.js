import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { ArrowLeft } from 'react-feather'; // Make sure you installed react-feather

const Login = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      const response = await axios.post('http://localhost:8080/api/auth/login', {
        email,
        password,
      });

      // Store token if using JWT
      if (response.data.token) {
        localStorage.setItem('token', response.data.token);
        console.log('✅ Login successful');
        navigate('/dashboard');
      } else {
        setError('❌ Invalid response from server.');
      }
    } catch (err) {
      console.error('Login failed:', err);
      setError('❌ Invalid email or password.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-tr from-blue-900 to-blue-700 px-4">
      <div className="relative bg-white p-8 rounded-lg shadow-xl w-full max-w-sm">

        {/* 🔙 Back to Home Button */}
        <button
          onClick={() => navigate('/')}
          className="absolute top-4 left-4 text-blue-700 hover:text-blue-900"
        >
          <ArrowLeft size={20} />
        </button>

        <h2 className="text-3xl font-bold text-blue-800 text-center mb-6">Welcome Back</h2>

        {error && <div className="bg-red-100 text-red-700 text-sm p-2 mb-4 rounded">{error}</div>}

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label htmlFor="email" className="block text-blue-800 font-medium">Email</label>
            <input
              type="email"
              id="email"
              className="w-full px-4 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>

          <div>
            <label htmlFor="password" className="block text-blue-800 font-medium">Password</label>
            <input
              type="password"
              id="password"
              className="w-full px-4 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>

          <button
            type="submit"
            className="w-full bg-blue-800 text-white py-2 rounded font-semibold hover:bg-blue-700 transition"
            disabled={loading}
          >
            {loading ? 'Logging In...' : 'Log In'}
          </button>
        </form>

        <p className="text-sm text-blue-800 mt-4 text-center">
          Don't have an account?{' '}
          <span
            className="text-blue-500 hover:underline cursor-pointer"
            onClick={() => navigate('/register')}
          >
            Sign Up
          </span>
        </p>
      </div>
    </div>
  );
};

export default Login;
