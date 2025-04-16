// src/pages/BookingHistory.js
import React, { useEffect, useState } from "react";
import axios from "axios";

const BookingHistory = () => {
  const [appointments, setAppointments] = useState([]);

  useEffect(() => {
    axios.get("http://localhost:8080/api/appointments/patient/4") // Replace with dynamic patientId
      .then(res => setAppointments(res.data))
      .catch(err => console.error("Error loading history:", err));
  }, []);

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4 text-center">My Booking History</h1>
      <ul className="space-y-4">
        {appointments.map((appt) => (
          <li key={appt.id} className="bg-white rounded-xl shadow p-4">
            <p><strong>Doctor:</strong> {appt.doctor.name}</p>
            <p><strong>Date:</strong> {appt.appointmentDate}</p>
            <p><strong>Time:</strong> {appt.appointmentTime}</p>
            <p><strong>Status:</strong> {appt.status}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default BookingHistory;
