import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { FaHome, FaHospital, FaUserMd, FaCity, FaHistory, FaSignOutAlt } from 'react-icons/fa'; // Import logout icon

function TopNav() {
  const navigate = useNavigate();

  const handleLogout = () => {
    // Clear token or localStorage/sessionStorage data if needed
    localStorage.removeItem('token'); // Adjust key as needed
    // Redirect to login page
    navigate('/login');
  };

  return (
    <div className="bg-blue-800 p-4 flex justify-between items-center text-white">
      {/* Left side: Logo */}
      <div className="text-xl font-semibold">
        <Link to="/dashboard" className="flex items-center space-x-2">
          <span className="text-white">AMSHC</span>
        </Link>
      </div>

      {/* Center: Navigation links */}
      <div className="flex space-x-8">
        <Link to="/dashboard" className="flex flex-col items-center text-white">
          <FaHome size={30} />
          <span className="text-sm">Home</span>
        </Link>

        <Link to="/booking" className="flex flex-col items-center text-white">
          <FaHospital size={30} />
          <span className="text-sm">Book now</span>
        </Link>

        <Link to="/doctor" className="flex flex-col items-center text-white">
          <FaUserMd size={30} />
          <span className="text-sm">Doctors</span>
        </Link>

        <Link to="/hospital-city-creator" className="flex flex-col items-center text-white">
          <FaCity size={30} />
          <span className="text-sm">Hospital & City</span>
        </Link>

        <Link to="/booking-history" className="flex flex-col items-center text-white">
          <FaHistory size={30} />
          <span className="text-sm">Booking History</span>
        </Link>
      </div>

      {/* Right side: Settings, Profile, Logout */}
      <div className="flex items-center space-x-6">
        <div className="text-white">Settings</div>
        <div className="text-white">Profile</div>

        {/* Logout icon */}
        <button
          onClick={handleLogout}
          className="flex flex-col items-center text-white hover:text-red-400"
        >
          <FaSignOutAlt size={30} />
          <span className="text-sm">Logout</span>
        </button>
      </div>
    </div>
  );
}

export default TopNav;
