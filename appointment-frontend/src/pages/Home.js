import React from 'react';
import { Link } from 'react-router-dom';
import { FaHospital, FaCalendarCheck, FaUserMd, FaHistory, FaHome } from 'react-icons/fa'; // Updated icons

function Home() {
  return (
    <div className="bg-gradient-to-r from-blue-500 via-blue-700 to-blue-900 min-h-screen text-white">
      <div className="container mx-auto p-8">
        <h1 className="text-4xl font-semibold text-center mb-8">Appointment Management System</h1>
        <p className="text-xl text-center mb-16">Streamlining your healthcare experience</p>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          {/* Book Appointment */}
          <Link
            to="/book-appointment"
            className="flex flex-col items-center bg-white text-blue-700 p-6 rounded-lg shadow-xl hover:shadow-2xl transition-all duration-300"
          >
            <FaCalendarCheck size={40} />
            <h3 className="mt-4 text-xl">Book Appointment</h3>
          </Link>

          {/* Choose Doctor */}
          <Link
            to="/choose-doctor"
            className="flex flex-col items-center bg-white text-blue-700 p-6 rounded-lg shadow-xl hover:shadow-2xl transition-all duration-300"
          >
            <FaUserMd size={40} />
            <h3 className="mt-4 text-xl">Choose Doctor</h3>
          </Link>

          {/* Hospital & City Creator */}
          <Link
            to="/hospital-city-creator"
            className="flex flex-col items-center bg-white text-blue-700 p-6 rounded-lg shadow-xl hover:shadow-2xl transition-all duration-300"
          >
            <FaHospital size={40} />
            <h3 className="mt-4 text-xl">Create Hospital & City</h3>
          </Link>

          {/* Booking History */}
          <Link
            to="/booking-history"
            className="flex flex-col items-center bg-white text-blue-700 p-6 rounded-lg shadow-xl hover:shadow-2xl transition-all duration-300"
          >
            <FaHistory size={40} />
            <h3 className="mt-4 text-xl">Booking History</h3>
          </Link>
        </div>
      </div>
    </div>
  );
}

export default Home;
