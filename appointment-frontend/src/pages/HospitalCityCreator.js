import React, { useState } from 'react';

function HospitalCityCreator() {
  const [cityName, setCityName] = useState("");
  const [hospitalName, setHospitalName] = useState("");

  const handleCreateCity = async () => {
    try {
      const response = await fetch('http://your-api-url/api/cities', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name: cityName }),
      });
      if (response.ok) {
        alert('City Created!');
        setCityName(""); // Clear input field
      }
    } catch (error) {
      console.error('Error creating city:', error);
    }
  };

  const handleCreateHospital = async () => {
    try {
      const response = await fetch('http://your-api-url/api/hospitals', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name: hospitalName }),
      });
      if (response.ok) {
        alert('Hospital Created!');
        setHospitalName(""); // Clear input field
      }
    } catch (error) {
      console.error('Error creating hospital:', error);
    }
  };

  return (
    <div className="p-8 bg-blue-100">
      <h2 className="text-3xl font-semibold mb-6">Create Hospital and City</h2>

      <div className="mb-6">
        <label className="block mb-2 text-lg">City Name</label>
        <input
          type="text"
          placeholder="Enter city name"
          value={cityName}
          onChange={(e) => setCityName(e.target.value)}
          className="w-full p-2 border border-gray-300 rounded mb-4"
        />
        <button onClick={handleCreateCity} className="bg-blue-500 text-white py-2 px-4 rounded">Create City</button>
      </div>

      <div>
        <label className="block mb-2 text-lg">Hospital Name</label>
        <input
          type="text"
          placeholder="Enter hospital name"
          value={hospitalName}
          onChange={(e) => setHospitalName(e.target.value)}
          className="w-full p-2 border border-gray-300 rounded mb-4"
        />
        <button onClick={handleCreateHospital} className="bg-blue-500 text-white py-2 px-4 rounded">Create Hospital</button>
      </div>
    </div>
  );
}

export default HospitalCityCreator;
