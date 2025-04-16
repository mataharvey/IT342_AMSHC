import React from 'react';
import { Link } from 'react-router-dom'; // Import Link for routing
import { FaHome, FaHospital, FaUserMd, FaCity, FaHistory } from 'react-icons/fa'; // Updated icons

function TopNav() {
  return (
    <div className="bg-blue-800 p-4 flex justify-between items-center text-white">
      {/* Left side: Logo */}
      <div className="text-xl font-semibold">
        <Link to="/dashboard" className="flex items-center space-x-2"> {/* Redirect to dashboard or home page */}

          <span className="text-white">AMSHC</span>
        </Link>
      </div>

      {/* Center: Navigation links */}
      <div className="flex space-x-8">
        {/* Home (Redirect to Dashboard or home page) */}
        <Link to="/dashboard" className="flex flex-col items-center text-white"> {/* Redirect to Dashboard */}
          <FaHome size={30} />
          <span className="text-sm">Home</span>
        </Link>

        {/* Book Appointment */}
        <Link to="/booking" className="flex flex-col items-center text-white">
          <FaHospital size={30} />
          <span className="text-sm">Book now</span>
        </Link>

        {/* Doctors Page (CRUD) */}
        <Link to="/doctor" className="flex flex-col items-center text-white">
          <FaUserMd size={30} />
          <span className="text-sm">Doctors</span>
        </Link>

        {/* Hospital and City Creator */}
        <Link to="/hospital-city-creator" className="flex flex-col items-center text-white">
          <FaCity size={30} />
          <span className="text-sm">Hospital & City</span>
        </Link>

        {/* Booking History */}
        <Link to="/booking-history" className="flex flex-col items-center text-white">
          <FaHistory size={30} />
          <span className="text-sm">Booking History</span>
        </Link>
      </div>

      {/* Right side: Add additional icons or settings */}
      <div className="flex items-center space-x-4">
        <div className="text-white">Settings</div>
        <div className="text-white">Profile</div>
      </div>
    </div>
  );
}

export default TopNav;
