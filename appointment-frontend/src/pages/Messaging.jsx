import React, { useEffect, useState } from 'react';
import axios from 'axios';

const Messaging = () => {
  const [search, setSearch] = useState('');
  const [doctors, setDoctors] = useState([]);
  const [selectedDoctor, setSelectedDoctor] = useState(null);
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState('');

  const token = localStorage.getItem('token');
  const userId = localStorage.getItem('userId');

  useEffect(() => {
    axios.get('http://localhost:8080/api/doctors', {
      headers: { Authorization: `Bearer ${token}` }
    })
    .then(res => setDoctors(res.data))
    .catch(err => console.error('Failed to fetch doctors', err));
  }, []);

  useEffect(() => {
    if (!selectedDoctor) return;

    const fetchMessages = () => {
      axios.get('http://localhost:8080/api/messages/conversation', {
        params: {
          user1: userId,
          user2: selectedDoctor.id,
          page: 0,
          size: 50,
          sortBy: 'timestamp',
          sortDir: 'asc'
        },
        headers: { Authorization: `Bearer ${token}` }
      })
      .then(res => setMessages(res.data.content))
      .catch(err => console.error('Failed to load messages', err));
    };

    fetchMessages();
    const interval = setInterval(fetchMessages, 3000);
    return () => clearInterval(interval);
  }, [selectedDoctor, userId, token]);

  const handleSelectDoctor = (doctor) => {
    setSelectedDoctor(doctor);
  };

  const handleSendMessage = () => {
    if (!newMessage.trim() || !selectedDoctor) return;

    axios.post('http://localhost:8080/api/messages/send', {
      senderId: userId,
      receiverId: selectedDoctor.id,
      senderRole: 'PATIENT', // or 'DOCTOR' depending on login
      content: newMessage
    }, {
      headers: { Authorization: `Bearer ${token}` }
    })
    .then(() => {
      setMessages(prev => [...prev, {
        senderId: userId,
        content: newMessage
      }]);
      setNewMessage('');
    })
    .catch(err => console.error('Send failed', err));
  };

  return (
    <div className="p-6 min-h-screen bg-gray-50">
      <h1 className="text-2xl font-bold text-center text-blue-800 mb-4">💬 Messaging</h1>

      <div className="flex gap-6">
        {/* Left: Doctor Search */}
        <div className="w-1/3">
          <input
            type="text"
            placeholder="Search doctor or specialization"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            className="w-full p-3 rounded-lg border"
          />
          <ul className="mt-4 space-y-2">
            {doctors
              .filter(d =>
                (d.name && d.name.toLowerCase().includes(search.toLowerCase())) ||
                (d.specialization && d.specialization.toLowerCase().includes(search.toLowerCase()))
              )
              .map(doctor => (
                <li
                  key={doctor.id}
                  className={`cursor-pointer p-3 rounded-lg hover:bg-blue-100 border ${selectedDoctor?.id === doctor.id ? 'bg-blue-200' : ''}`}
                  onClick={() => handleSelectDoctor(doctor)}
                >
                  <strong>{doctor.name}</strong> – {doctor.specialization}
                </li>
              ))}
          </ul>
        </div>

        {/* Right: Chat */}
        <div className="w-2/3 bg-white rounded-xl shadow p-4 border h-[500px] flex flex-col">
          {selectedDoctor ? (
            <>
              <h2 className="text-lg font-semibold mb-2 text-blue-700">Chat with {selectedDoctor.name}</h2>
              <div className="flex-1 overflow-y-auto space-y-2 border-t border-b py-3 mb-3">
                {messages.map((msg, idx) => (
                  <div
                    key={idx}
                    className={`max-w-xs px-4 py-2 rounded-lg text-white ${
                      msg.senderId == userId ? 'bg-blue-600 self-end' : 'bg-gray-400 self-start'
                    }`}
                  >
                    {msg.content}
                  </div>
                ))}
              </div>

              <div className="flex">
                <input
                  type="text"
                  value={newMessage}
                  onChange={(e) => setNewMessage(e.target.value)}
                  className="flex-1 p-3 rounded-l-lg border border-gray-300"
                  placeholder="Type your message..."
                />
                <button
                  onClick={handleSendMessage}
                  className="bg-blue-600 text-white px-5 rounded-r-lg hover:bg-blue-700"
                >
                  Send
                </button>
              </div>
            </>
          ) : (
            <p className="text-gray-500 text-center mt-20">Select a doctor to start chatting</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default Messaging;
