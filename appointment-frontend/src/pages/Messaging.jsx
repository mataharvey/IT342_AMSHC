import React, { useEffect, useRef, useState } from 'react';
import axios from 'axios';

const Messaging = () => {
  const [search, setSearch] = useState('');
  const [users, setUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState('');
  const [loading, setLoading] = useState(false);

  const token = localStorage.getItem('token');
  const userId = parseInt(localStorage.getItem('userId'));
  const role = localStorage.getItem('role');
  const bottomRef = useRef(null); // 🧹 Auto scroll ref

  useEffect(() => {
    if (!token) return;

    const fetchUsers = async () => {
      try {
        const endpoint = role === 'PATIENT' 
          ? 'http://localhost:8080/api/doctors'
          : 'http://localhost:8080/api/patients';
        const res = await axios.get(endpoint, {
          headers: { Authorization: `Bearer ${token}` }
        });
        setUsers(res.data);
      } catch (err) {
        console.error('❌ Failed to fetch users:', err.message);
      }
    };

    fetchUsers();
  }, [token, role]);

  useEffect(() => {
    if (!selectedUser) return;

    const fetchMessages = async () => {
      setLoading(true);
      try {
        const res = await axios.get('http://localhost:8080/api/messages/conversation', {
          params: {
            user1: userId,
            user2: selectedUser.userId,
            page: 0,
            size: 100,
            sortBy: 'timestamp',
            sortDir: 'asc'
          },
          headers: { Authorization: `Bearer ${token}` }
        });
        setMessages(res.data.content);
        scrollToBottom();
      } catch (err) {
        console.error('Failed to load messages', err);
      }
      setLoading(false);
    };

    fetchMessages();
    const interval = setInterval(fetchMessages, 5000); 
    return () => clearInterval(interval);
  }, [selectedUser, userId, token]);

  const scrollToBottom = () => {
    bottomRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  const handleSelectUser = (user) => {
    setSelectedUser(user);
    setMessages([]);
  };

  const handleSendMessage = async () => {
    if (!newMessage.trim() || !selectedUser) return;

    try {
      await axios.post('http://localhost:8080/api/messages/send', {
        senderId: userId,
        receiverId: selectedUser.userId,
        senderRole: role,
        content: newMessage
      }, {
        headers: { Authorization: `Bearer ${token}` }
      });

      const newMsg = {
        senderId: userId,
        content: newMessage,
        timestamp: new Date().toISOString()
      };
      setMessages((prev) => [...prev, newMsg]);
      setNewMessage('');
      scrollToBottom();
    } catch (err) {
      console.error('Send failed', err);
    }
  };

  return (
    <div className="p-6 min-h-screen bg-gray-50">
      <h1 className="text-2xl font-bold text-center text-blue-800 mb-4">💬 Messaging</h1>

      <div className="flex gap-6">
        {/* Left panel */}
        <div className="w-1/3">
          <input
            type="text"
            placeholder={role === 'PATIENT' ? "Search doctor..." : "Search patient..."}
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            className="w-full p-3 rounded-lg border"
          />
          <ul className="mt-4 space-y-2">
            {users
              .filter(u => u.name?.toLowerCase().includes(search.toLowerCase()))
              .map(u => (
                <li
                  key={u.id}
                  className={`cursor-pointer p-3 rounded-lg hover:bg-blue-100 border ${selectedUser?.id === u.id ? 'bg-blue-200' : ''}`}
                  onClick={() => handleSelectUser(u)}
                >
                  <strong>{u.name}</strong> {u.specialization && `– ${u.specialization}`}
                </li>
              ))}
          </ul>
        </div>

        {/* Right panel */}
        <div className="w-2/3 bg-white rounded-xl shadow p-4 border h-[500px] flex flex-col">
          {selectedUser ? (
            <>
              <h2 className="text-lg font-semibold mb-2 text-blue-700">
                Chat with {selectedUser.name}
              </h2>

              <div className="flex-1 overflow-y-auto space-y-4 border-t border-b py-3 mb-3">
                {loading ? (
                  <div className="text-gray-500 text-center">Loading messages...</div>
                ) : (
                  <>
                    {messages.map((msg, idx) => (
                      <div
                        key={idx}
                        className={`flex items-end ${
                          msg.senderId === userId ? 'justify-end' : 'justify-start'
                        }`}
                      >
                        {msg.senderId !== userId && (
                          <img
                            src={selectedUser.avatarUrl || '/default-avatar.png'}
                            alt="Profile"
                            className="w-8 h-8 rounded-full mr-2"
                          />
                        )}
                        <div
                          className={`max-w-xs px-4 py-2 rounded-2xl text-sm ${
                            msg.senderId === userId ? 'bg-blue-600 text-white' : 'bg-gray-300 text-black'
                          }`}
                        >
                          {msg.content}
                        </div>
                      </div>
                    ))}
                    <div ref={bottomRef} />
                  </>
                )}
              </div>

              <div className="flex">
                <input
                  type="text"
                  value={newMessage}
                  onChange={(e) => setNewMessage(e.target.value)}
                  className="flex-1 p-3 rounded-l-lg border border-gray-300"
                  placeholder="Type your message..."
                  onKeyPress={(e) => {
                    if (e.key === 'Enter') handleSendMessage();
                  }}
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
            <p className="text-gray-500 text-center mt-20">
              {role === 'PATIENT' ? "Select a doctor to start chatting" : "Select a patient to start chatting"}
            </p>
          )}
        </div>
      </div>
    </div>
  );
};

export default Messaging;
