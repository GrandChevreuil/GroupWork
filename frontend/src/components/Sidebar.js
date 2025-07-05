import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import AuthService from "../services/auth.service";

const Sidebar = () => {
  const [showModeratorBoard, setShowModeratorBoard] = useState(false);
  const [showAdminBoard, setShowAdminBoard] = useState(false);
  const [currentUser, setCurrentUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const user = AuthService.getCurrentUser();
    if (user) {
      setCurrentUser(user);
      setShowModeratorBoard(user.roles.includes("SUPERVISING_STAFF"));
      setShowAdminBoard(user.roles.includes("ADMIN_SYSTEM"));
    }
  }, []);

  const handleLogout = () => {
    AuthService.logout();
    setCurrentUser(null);
    navigate("/login");
  };

  return (
    <aside className="fixed inset-y-0 left-0 w-64 bg-gray-800 text-gray-100 flex flex-col">
      <div className="px-6 py-4 text-xl font-bold border-b border-gray-700">
        GROUPWORK
      </div>
      <nav className="flex-1 overflow-y-auto px-4 py-6 space-y-2">
        <Link to="/home" className="block px-3 py-2 rounded hover:bg-gray-700">
          Home
        </Link>
        {showModeratorBoard && (
          <Link to="/mod" className="block px-3 py-2 rounded hover:bg-gray-700">
            Moderator Board
          </Link>
        )}
        {showAdminBoard && (
          <Link to="/admin" className="block px-3 py-2 rounded hover:bg-gray-700">
            Admin Board
          </Link>
        )}
        {currentUser && !currentUser.roles.includes("ADMIN_SYSTEM") && (
          <Link to="/user" className="block px-3 py-2 rounded hover:bg-gray-700">
            User
          </Link>
        )}
      </nav>
      <div className="px-4 py-6 border-t border-gray-700">
        {currentUser ? (
          <>
            <Link to="/profile" className="block px-3 py-2 rounded hover:bg-gray-700">
              {currentUser.username}
            </Link>
            <button onClick={handleLogout} className="w-full text-left px-3 py-2 rounded hover:bg-gray-700">
              Logout
            </button>
          </>
        ) : (
          <>
            <Link to="/login" className="block px-3 py-2 rounded hover:bg-gray-700">
              Login
            </Link>
            <Link to="/register" className="block px-3 py-2 rounded hover:bg-gray-700">
              Sign Up
            </Link>
          </>
        )}
      </div>
    </aside>
  );
};

export default Sidebar;