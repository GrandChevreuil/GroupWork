import React, { useState, useEffect } from "react";
import { Routes, Route } from "react-router-dom";
import "./index.css";  // Tailwind CSS
import Sidebar from "./components/Sidebar";
import PublicNavbar from "./components/PublicNavbar";
import { useAppNotifications } from "./hooks/useAppNotifications";

import AuthService from "./services/auth.service";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Home from "./pages/Home";
import Profile from "./pages/Profile";
import BoardUser from "./pages/BoardUser";
import BoardModerator from "./pages/BoardModerator";
import BoardAdmin from "./pages/BoardAdmin";

// import AuthVerify from "./common/AuthVerify";
import EventBus from "./common/EventBus";

/**
 * Composant principal de l'application
 * @author Mathis Mauprivez
 */
const App = () => {
  const [showModeratorBoard, setShowModeratorBoard] = useState(false);
  const [showAdminBoard, setShowAdminBoard] = useState(false);
  const [currentUser, setCurrentUser] = useState(undefined);
  const [loading, setLoading] = useState(true);
  const { showSuccess } = useAppNotifications();

  useEffect(() => {
    const user = AuthService.getCurrentUser();

    if (user) {
      setCurrentUser(user);
      setShowModeratorBoard(user.roles.includes("SUPERVISING_STAFF"));
      setShowAdminBoard(user.roles.includes("ADMIN_SYSTEM"));
    }

    setLoading(false);

    EventBus.on("logout", () => {
      logOut();
    });

    return () => {
      EventBus.remove("logout");
    };
  }, []);

  const logOut = () => {
    AuthService.logout();
    setShowModeratorBoard(false);
    setShowAdminBoard(false);
    setCurrentUser(undefined);
    showSuccess("Vous avez été déconnecté avec succès");
    // window.location.href = "/login"; // Redirection forcée vers la page de login
  };

  return (
    <div className="min-h-screen bg-page">
      {!loading && (
        <>
          {currentUser ? (
    // Interface utilisateur connecté (avec sidebar)
            <div className="flex">
              <Sidebar />
              <div className="flex-1 p-4">
                <div className="container mx-auto">
                  <Routes>
                    <Route exact path={"/"} element={<Home />} />
                    <Route exact path={"/home"} element={<Home />} />
                    <Route exact path="/profile" element={<Profile />} />
                    <Route path="/user" element={<BoardUser />} />
                    <Route path="/mod" element={<BoardModerator />} />
                    <Route path="/admin" element={<BoardAdmin />} />

                    {/* Rediriger vers home si déjà connecté */}
                    <Route path="/login" element={<Home />} />
                    <Route path="/register" element={<Home />} />
                  </Routes>
                </div>
              </div>
            </div>
          ) : (
            // Interface utilisateur non connecté (avec navbar publique)
            <div className="flex flex-col min-h-screen">
              <PublicNavbar />
              <div className="flex-1 p-4">
                <div className="container mx-auto">
                  <Routes>
                    <Route exact path={"/"} element={<Home />} />
                    <Route exact path={"/home"} element={<Home />} />
                    <Route exact path="/login" element={<Login />} />
                    <Route exact path="/register" element={<Register />} />

                      {/* Rediriger vers login si non connecté */}
                      <Route path="/profile" element={<Login />} />
                      <Route path="/user" element={<Login />} />
                      <Route path="/mod" element={<Login />} />
                      <Route path="/admin" element={<Login />} />
                    </Routes>
                  </div>
              </div>
            </div>
          )}
        </>
      )}

      {/* <AuthVerify logOut={logOut}/> */}
    </div>
  );
};

export default App;
