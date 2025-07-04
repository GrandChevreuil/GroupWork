import React from "react";
import { Navigate } from "react-router-dom";
import AuthService from "../services/auth.service";

const Profile = () => {
  const currentUser = AuthService.getCurrentUser();
  console.log("localstorage", localStorage);

  // Si pas d'utilisateur, on redirige à /login (donc pas d'erreur de lecture)
  if (!currentUser) {
    return <Navigate to="/login" replace />;
  }

  return (
    <div className="container">
      <header className="jumbotron">
        <h3>
          <strong>{currentUser.username}</strong> Profile
        </h3>
      </header>
      <p>
        <strong>Id:</strong> {currentUser.id}
      </p>
      <p>
        <strong>Email:</strong> {currentUser.email}
      </p>
      <p>
        <strong>Classe:</strong> {currentUser.classeName || '—'}
      </p>
      <strong>Authorities:</strong>
      <ul>
        {currentUser.roles.map((role, idx) => (
          <li key={idx}>{role}</li>
        ))}
      </ul>
    </div>
  );
};

export default Profile;
