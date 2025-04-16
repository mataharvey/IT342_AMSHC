import React from "react";

function Dashboard() {
  const role = localStorage.getItem("role");
  const userId = localStorage.getItem("userId");

  return (
    <div className="flex items-center justify-center h-screen bg-yellow-100">
      <div className="text-center">
        <h1 className="text-3xl font-bold">Welcome to the Dashboard!</h1>
        <p className="mt-2 text-lg">
          Role: <strong>{role}</strong>
        </p>
        <p>User ID: {userId}</p>
      </div>
    </div>
  );
}

export default Dashboard;
