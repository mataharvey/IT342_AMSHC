import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { FaPlus } from 'react-icons/fa'; // Add icon for "Add Doctor"

const Doctor = () => {
  const [doctors, setDoctors] = useState([]);
  const [name, setName] = useState('');
  const [specialization, setSpecialization] = useState('');
  const [contact, setContact] = useState('');
  const [isAddDoctorOpen, setIsAddDoctorOpen] = useState(false); // Add this line for modal state

  // Fetching doctor data from API
  useEffect(() => {
    const fetchDoctors = async () => {
      try {
        const token = localStorage.getItem('token'); // Get the token from localStorage

        // Check if token exists before making the request
        if (token) {
          const response = await axios.get('http://localhost:8080/api/doctors', {
            headers: {
              Authorization: `Bearer ${token}`, // Ensure token is in the request headers
            },
          });
          setDoctors(response.data); // Update doctors state with the data from API
        } else {
          console.log("No token found. Please log in.");
        }
      } catch (error) {
        console.error("Error fetching doctors:", error);
      }
    };

    fetchDoctors();
  }, []); // Only run this once when the component is mounted

  const handleAddDoctor = async () => {
    try {
      const doctor = { name, specialization, contact };
      const token = localStorage.getItem('token'); // Get token from localStorage
      console.log("Token being sent:", token); // Log the token to check if it's being fetched properly

      const response = await axios.post('http://localhost:8080/api/doctors', doctor, {
        headers: {
          Authorization: `Bearer ${token}`, // Ensure token is being sent correctly
        },
      });

      // Check the response and add the new doctor to the list
      if (response.data) {
        setDoctors((prevDoctors) => [...prevDoctors, response.data]);
        setName('');
        setSpecialization('');
        setContact('');
        setIsAddDoctorOpen(false);
        alert('Doctor added successfully');
      }
    } catch (error) {
      console.error("Error adding doctor:", error);
      alert('Failed to add doctor');
    }
  };


  return (
    <div className="container p-4">
      <h2 className="text-3xl font-bold mb-4">Doctors</h2>

      <div className="mb-4">
        <button
          className="bg-blue-600 text-white py-2 px-4 rounded-md flex items-center"
          onClick={() => setIsAddDoctorOpen(true)} // Open Add Doctor modal
        >
          <FaPlus className="mr-2" />
          Add Doctor
        </button>
      </div>

      <h3 className="text-xl font-semibold mb-4">Doctor List</h3>
      {doctors.length > 0 ? (
        <div className="grid grid-cols-3 gap-4">
          {doctors.map((doctor) => (
            <div key={doctor.id} className="border p-4 rounded-lg">
              <h4 className="text-xl font-medium">{doctor.name}</h4>
              <p><strong>Specialization:</strong> {doctor.specialization}</p>
              <p><strong>Contact:</strong> {doctor.contact}</p>
              <p><strong>Gender:</strong> {doctor.gender}</p>
              <p><strong>Experience:</strong> {doctor.experience} years</p>
            </div>
          ))}
        </div>
      ) : (
        <p>No doctors available.</p>
      )}

      {/* Add Doctor Form (Modal) */}
      {isAddDoctorOpen && (
        <div className="modal">
          <h3>Add New Doctor</h3>
          <form
            onSubmit={(e) => {
              e.preventDefault();
              handleAddDoctor();
            }}
          >
            <input
              type="text"
              placeholder="Name"
              value={name}
              onChange={(e) => setName(e.target.value)}
              className="mb-2 p-2 border"
              required
            />
            <input
              type="text"
              placeholder="Specialization"
              value={specialization}
              onChange={(e) => setSpecialization(e.target.value)}
              className="mb-2 p-2 border"
              required
            />
            <input
              type="text"
              placeholder="Contact"
              value={contact}
              onChange={(e) => setContact(e.target.value)}
              className="mb-2 p-2 border"
              required
            />
            <button type="submit" className="bg-blue-600 text-white py-2 px-4 rounded-md">
              Add Doctor
            </button>
            <button type="button" className="ml-2 text-red-500" onClick={() => setIsAddDoctorOpen(false)}>
              Cancel
            </button>
          </form>
        </div>
      )}
    </div>
  );
};

export default Doctor;
