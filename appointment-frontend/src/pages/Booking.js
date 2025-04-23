import React, { useState } from 'react';
import { Calendar, Clock, User, UserPlus, Check } from 'react-feather';

function Booking() {
  const [doctor, setDoctor] = useState("");
  const [appointmentTime, setAppointmentTime] = useState("");
  const [patientName, setPatientName] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [success, setSuccess] = useState(false);

  const handleBookAppointment = async () => {
    try {
      setIsSubmitting(true);
      const response = await fetch('http://your-api-url/api/appointments', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ doctor, appointmentTime, patientName }),
      });
      if (response.ok) {
        setSuccess(true);
        setTimeout(() => setSuccess(false), 3000);
        alert('Appointment booked successfully!');
      }
    } catch (error) {
      console.error('Error booking appointment:', error);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-white p-4 md:p-8">
      <div className="max-w-3xl mx-auto">
        {/* Header */}
        <div className="bg-white rounded-t-2xl shadow-md p-6 border-b border-blue-100">
          <h2 className="text-3xl font-bold text-blue-800 flex items-center">
            <Calendar className="mr-3 text-blue-600" size={28} />
            Book Your Appointment
          </h2>
          <p className="text-gray-600 mt-2">Schedule a consultation with our healthcare professionals</p>
        </div>

        {/* Form */}
        <div className="bg-white rounded-b-2xl shadow-md p-6 mb-8">
          <div className="space-y-6">
            {/* Doctor Field */}
            <div className="form-group">
              <label className="block mb-2 text-sm font-medium text-gray-700">Doctor Name</label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <UserPlus className="h-5 w-5 text-blue-500" />
                </div>
                <input
                  type="text"
                  placeholder="Enter doctor name"
                  value={doctor}
                  onChange={(e) => setDoctor(e.target.value)}
                  className="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all"
                />
              </div>
            </div>

            {/* Appointment Time Field */}
            <div className="form-group">
              <label className="block mb-2 text-sm font-medium text-gray-700">Appointment Date & Time</label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <Clock className="h-5 w-5 text-blue-500" />
                </div>
                <input
                  type="datetime-local"
                  value={appointmentTime}
                  onChange={(e) => setAppointmentTime(e.target.value)}
                  className="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all"
                />
              </div>
            </div>

            {/* Patient Name Field */}
            <div className="form-group">
              <label className="block mb-2 text-sm font-medium text-gray-700">Patient Name</label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <User className="h-5 w-5 text-blue-500" />
                </div>
                <input
                  type="text"
                  placeholder="Enter patient name"
                  value={patientName}
                  onChange={(e) => setPatientName(e.target.value)}
                  className="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all"
                />
              </div>
            </div>

            {/* Submit Button */}
            <button 
              onClick={handleBookAppointment} 
              disabled={isSubmitting}
              className={`w-full bg-blue-600 hover:bg-blue-700 text-white font-medium py-3 px-4 rounded-lg transition-all shadow-md hover:shadow-lg transform hover:-translate-y-0.5 flex justify-center items-center ${isSubmitting ? 'opacity-70 cursor-not-allowed' : ''}`}
            >
              {isSubmitting ? (
                <>
                  <svg className="animate-spin -ml-1 mr-2 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                    <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                    <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                  </svg>
                  Processing...
                </>
              ) : success ? (
                <>
                  <Check className="mr-2" size={20} />
                  Appointment Booked!
                </>
              ) : (
                'Book Appointment'
              )}
            </button>
          </div>
        </div>

        {/* Information Cards */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div className="bg-white p-6 rounded-xl shadow-md border-l-4 border-blue-500">
            <h3 className="font-bold text-lg text-gray-800 mb-2">Before Your Appointment</h3>
            <ul className="text-gray-600 space-y-2">
              <li className="flex items-start">
                <span className="text-blue-500 mr-2">•</span>
                Please arrive 15 minutes before your scheduled time
              </li>
              <li className="flex items-start">
                <span className="text-blue-500 mr-2">•</span>
                Bring your insurance card and ID
              </li>
              <li className="flex items-start">
                <span className="text-blue-500 mr-2">•</span>
                List of current medications
              </li>
            </ul>
          </div>

          <div className="bg-white p-6 rounded-xl shadow-md border-l-4 border-teal-500">
            <h3 className="font-bold text-lg text-gray-800 mb-2">Appointment Policy</h3>
            <ul className="text-gray-600 space-y-2">
              <li className="flex items-start">
                <span className="text-teal-500 mr-2">•</span>
                Cancellations require 24-hour notice
              </li>
              <li className="flex items-start">
                <span className="text-teal-500 mr-2">•</span>
                Late arrivals may need to reschedule
              </li>
              <li className="flex items-start">
                <span className="text-teal-500 mr-2">•</span>
                Telehealth options available upon request
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Booking;