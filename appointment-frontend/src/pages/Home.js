import React from "react";
import { useNavigate } from "react-router-dom";
import doctorImg from "../images/doctor ai.png";
import logo from "../images/amshc logo.png";

const Home = () => {
  const navigate = useNavigate();

  return (
    <div className="min-h-screen bg-white">
      {/* Navbar */}
      <div className="flex items-center justify-between px-6 py-4 shadow-md">
        {/* Logo on the left */}
        <div className="flex items-center">
          <img src={logo} alt="AMSHC Logo" className="h-12 w-auto mr-2" />
          <h1 className="text-xl font-bold text-gray-800">AMSHC</h1>
        </div>

        {/* Buttons on the right */}
        <div className="space-x-4">
          <button
            onClick={() => navigate("/login")}
            className="bg-blue-600 text-white px-5 py-2 rounded hover:bg-blue-700 transition"
          >
            Log In
          </button>
          <button
            onClick={() => navigate("/register")}
            className="bg-teal-500 text-white px-5 py-2 rounded hover:bg-teal-600 transition"
          >
            Sign Up
          </button>
        </div>
      </div>

      {/* Main Content */}
      <div className="flex flex-col items-center justify-center text-center px-6 mt-16">
        <h2 className="text-4xl font-bold text-gray-800 mb-4">
          Manage your entire practice in one app.
        </h2>
        <p className="text-lg text-gray-600 mb-6">
          ✔ Medical records <br />
          ✔ Appointment scheduling <br />
          ✔ Virtual consultations <br />
          ✔ Payments & billing
        </p>

        <img
          src={doctorImg}
          alt="Doctor"
          className="max-w-sm w-full h-auto shadow-xl rounded-lg"
        />
      </div>
    </div>
  );
};

export default Home;
