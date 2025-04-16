import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import TopNav from './components/TopNav'; // Import TopNav component
import Home from './pages/Home'; // Home page
import Booking from './pages/Booking'; // Booking page
import Doctor from './pages/Doctor'; // Doctors CRUD page (updated from ChooseDoctor)
import HospitalCityCreator from './pages/HospitalCityCreator'; // Hospital & City Creator page
import BookingHistory from './pages/BookingHistory'; // Booking History page
import Dashboard from './pages/Dashboard'; // Dashboard page
import Login from './pages/Login'; // Login page
import Register from './pages/Register'; // Register page

function App() {
  return (
    <Router>
      <Routes>
        {/* Public Routes (for login and registration) */}
        <Route path="/" element={<Login />} /> {/* Login page */}
        <Route path="/register" element={<Register />} /> {/* Register page */}

        {/* Protected Routes (after login, with TopNav shown) */}
        <Route path="/dashboard" element={<><TopNav /><Dashboard /></>} /> {/* Dashboard page */}
        <Route path="/booking" element={<><TopNav /><Booking /></>} /> {/* Booking page */}
        <Route path="/doctor" element={<><TopNav /><Doctor /></>} /> {/* Doctors CRUD page */}
        <Route path="/hospital-city-creator" element={<><TopNav /><HospitalCityCreator /></>} /> {/* Hospital and City Creator */}
        <Route path="/booking-history" element={<><TopNav /><BookingHistory /></>} /> {/* Booking History */}
      </Routes>
    </Router>
  );
}

export default App;
