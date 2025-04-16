import React, { useState } from 'react';

function Booking() {
  const [doctor, setDoctor] = useState("");
  const [appointmentTime, setAppointmentTime] = useState("");
  const [patientName, setPatientName] = useState("");

  const handleBookAppointment = async () => {
    try {
      const response = await fetch('http://your-api-url/api/appointments', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ doctor, appointmentTime, patientName }),
      });
      if (response.ok) {
        alert('Appointment booked successfully!');
      }
    } catch (error) {
      console.error('Error booking appointment:', error);
    }
  };

  return (
    <div className="p-8 bg-blue-100">
      <h2 className="text-3xl font-semibold mb-6">Book Appointment</h2>

      <div className="mb-6">
        <label className="block mb-2 text-lg">Doctor</label>
        <input
          type="text"
          placeholder="Enter doctor name"
          value={doctor}
          onChange={(e) => setDoctor(e.target.value)}
          className="w-full p-2 border border-gray-300 rounded mb-4"
        />
        <label className="block mb-2 text-lg">Appointment Time</label>
        <input
          type="datetime-local"
          value={appointmentTime}
          onChange={(e) => setAppointmentTime(e.target.value)}
          className="w-full p-2 border border-gray-300 rounded mb-4"
        />
        <label className="block mb-2 text-lg">Patient Name</label>
        <input
          type="text"
          placeholder="Enter patient name"
          value={patientName}
          onChange={(e) => setPatientName(e.target.value)}
          className="w-full p-2 border border-gray-300 rounded mb-4"
        />
        <button onClick={handleBookAppointment} className="bg-blue-500 text-white py-2 px-4 rounded">Book Appointment</button>
      </div>
    </div>
  );
}

export default Booking;
