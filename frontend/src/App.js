import React, { useState, useEffect } from "react";
import { Routes, Route, Link } from "react-router-dom";
import "./index.css";  // Tailwind CSS
import Sidebar from "./components/Sidebar";

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

const App = () => {
  const [showModeratorBoard, setShowModeratorBoard] = useState(false);
  const [showAdminBoard, setShowAdminBoard] = useState(false);
  const [currentUser, setCurrentUser] = useState(undefined);

  useEffect(() => {
    const user = AuthService.getCurrentUser();

    if (user) {
      setCurrentUser(user);
      setShowModeratorBoard(user.roles.includes("SUPERVISING_STAFF"));
      setShowAdminBoard(user.roles.includes("ADMIN_SYSTEM"));
    }

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
  };

  return (
    <div className="flex">
      <Sidebar />
      <div className="flex-1 p-4">
        <nav className="bg-white shadow-md rounded-md px-4 py-2 mb-4">
          <div className="container mx-auto flex justify-between items-center">
            <Link to={"/"} className="text-xl font-semibold">
              GROUPWORK
            </Link>
            <div className="flex space-x-4">
              <Link to={"/home"} className="text-gray-700 hover:text-blue-500">
                Home
              </Link>

              {showModeratorBoard && (
                <Link to={"/mod"} className="text-gray-700 hover:text-blue-500">
                  Moderator Board
                </Link>
              )}

              {showAdminBoard && (
                <Link to={"/admin"} className="text-gray-700 hover:text-blue-500">
                  Admin Board
                </Link>
              )}

              {currentUser &&
                !currentUser.roles.includes("ADMIN_SYSTEM") && (
                  <Link to={"/user"} className="text-gray-700 hover:text-blue-500">
                    User
                  </Link>
                )}
            </div>

            <div className="flex items-center space-x-4">
              {currentUser ? (
                <>
                  <Link
                    to={"/profile"}
                    className="text-gray-700 hover:text-blue-500"
                  >
                    {currentUser.username}
                  </Link>
                  <a
                    href="/login"
                    className="text-gray-700 hover:text-blue-500"
                    onClick={logOut}
                  >
                    LogOut
                  </a>
                </>
              ) : (
                <>
                  <Link to={"/login"} className="text-gray-700 hover:text-blue-500">
                    Login
                  </Link>

                  <Link to={"/register"} className="text-gray-700 hover:text-blue-500">
                    Sign Up
                  </Link>
                </>
              )}
            </div>
          </div>
        </nav>

        <div className="container mx-auto">
          <Routes>
            <Route exact path={"/"} element={<Home />} />
            <Route exact path={"/home"} element={<Home />} />
            <Route exact path="/login" element={<Login />} />
            <Route exact path="/register" element={<Register />} />
            <Route exact path="/profile" element={<Profile />} />
            <Route path="/user" element={<BoardUser />} />
            <Route path="/mod" element={<BoardModerator />} />
            <Route path="/admin" element={<BoardAdmin />} />
          </Routes>
        </div>

        {/* <AuthVerify logOut={logOut}/> */}
      </div>
    </div>
  );
};

export default App;
