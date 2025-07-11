import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Text, Button } from "@mantine/core";
import { IconHome, IconUser, IconShield, IconSettings, IconLogout, IconUserCircle } from '@tabler/icons-react';
import { designTokens } from "../theme/designTokens";
import AuthService from "../services/auth.service";
import { useAppNotifications } from "../hooks/useAppNotifications";

const Sidebar = () => {
  const [showModeratorBoard, setShowModeratorBoard] = useState(false);
  const [showAdminBoard, setShowAdminBoard] = useState(false);
  const [currentUser, setCurrentUser] = useState(null);
  const navigate = useNavigate();
  const { showSuccess } = useAppNotifications();

  useEffect(() => {
    try {
      const user = AuthService.getCurrentUser();
      if (user) {
        setCurrentUser(user);
        setShowModeratorBoard(user.roles && user.roles.includes("SUPERVISING_STAFF"));
        setShowAdminBoard(user.roles && user.roles.includes("ADMIN_SYSTEM"));
      }
    } catch (error) {
      console.log("Error loading user data:", error);
    }
  }, []);

  const handleLogout = () => {
    try {
      AuthService.logout();
      setCurrentUser(null);
    showSuccess("Vous avez été déconnecté avec succès");
    window.location.href = "/login"; // Force un rafraîchissement complet
    } catch (error) {
      console.log("Error during logout:", error);
    }
  };

  return (
    <aside className="fixed inset-y-0 left-0 w-64 shadow-lg flex flex-col" style={{
      backgroundColor: designTokens.colors.container.primary.border,
      color: designTokens.colors.container.primary.content,
    }}>
      <div className="px-6 py-5" style={{
        borderBottom: `1px solid ${designTokens.colors.container.secondary.border}`,
      }}>
        <Text
          component="div"
          fw={700}
          size="xl"
          style={{
            color: '#ffffff',
            fontFamily: designTokens.typography.fontFamily.heading
          }}
        >
          GROUPWORK
        </Text>
      </div>

      <nav className="flex-1 overflow-y-auto py-6 px-4 space-y-2">
        <Link
          to="/home"
          className="flex items-center px-3 py-3 rounded-md transition-colors"
          style={{
            color: '#ffffff',
            fontFamily: designTokens.typography.fontFamily.body,
            fontSize: '0.95rem',
            fontWeight: 500
          }}
          onMouseEnter={(e) => e.currentTarget.style.backgroundColor = 'rgba(255, 255, 255, 0.1)'}
          onMouseLeave={(e) => e.currentTarget.style.backgroundColor = 'transparent'}
        >
          <IconHome size={20} style={{ marginRight: '10px' }} />
          Accueil
        </Link>

        {showModeratorBoard && (
          <Link
            to="/mod"
            className="flex items-center px-3 py-3 rounded-md transition-colors"
            style={{
              color: '#ffffff',
              fontFamily: designTokens.typography.fontFamily.body,
              fontSize: '0.95rem',
              fontWeight: 500
            }}
            onMouseEnter={(e) => e.currentTarget.style.backgroundColor = 'rgba(255, 255, 255, 0.1)'}
            onMouseLeave={(e) => e.currentTarget.style.backgroundColor = 'transparent'}
          >
            <IconShield size={20} style={{ marginRight: '10px' }} />
            Supervision
          </Link>
        )}

        {showAdminBoard && (
          <Link
            to="/admin"
            className="flex items-center px-3 py-3 rounded-md transition-colors"
            style={{
              color: '#ffffff',
              fontFamily: designTokens.typography.fontFamily.body,
              fontSize: '0.95rem',
              fontWeight: 500
            }}
            onMouseEnter={(e) => e.currentTarget.style.backgroundColor = 'rgba(255, 255, 255, 0.1)'}
            onMouseLeave={(e) => e.currentTarget.style.backgroundColor = 'transparent'}
          >
            <IconSettings size={20} style={{ marginRight: '10px' }} />
            Administration
          </Link>
        )}

        {currentUser && !currentUser.roles.includes("ADMIN_SYSTEM") && (
          <Link
            to="/user"
            className="flex items-center px-3 py-3 rounded-md transition-colors"
            style={{
              color: '#ffffff',
              fontFamily: designTokens.typography.fontFamily.body,
              fontSize: '0.95rem',
              fontWeight: 500
            }}
            onMouseEnter={(e) => e.currentTarget.style.backgroundColor = 'rgba(255, 255, 255, 0.1)'}
            onMouseLeave={(e) => e.currentTarget.style.backgroundColor = 'transparent'}
          >
            <IconUser size={20} style={{ marginRight: '10px' }} />
            Espace utilisateur
          </Link>
        )}
      </nav>

      <div className="px-4 py-4" style={{
        borderTop: `1px solid ${designTokens.colors.container.secondary.border}`,
      }}>
        {currentUser && (
          <>
            <Link
              to="/profile"
              className="flex items-center px-3 py-3 rounded-md transition-colors mb-2"
              style={{
                color: '#ffffff',
                fontFamily: designTokens.typography.fontFamily.body,
                fontSize: '0.95rem',
                fontWeight: 500
              }}
              onMouseEnter={(e) => e.currentTarget.style.backgroundColor = 'rgba(255, 255, 255, 0.1)'}
              onMouseLeave={(e) => e.currentTarget.style.backgroundColor = 'transparent'}
            >
              <IconUserCircle size={20} style={{ marginRight: '10px' }} />
              {currentUser.username}
            </Link>

            <Button
              fullWidth
              leftSection={<IconLogout size={18} />}
              onClick={handleLogout}
              style={{
                backgroundColor: designTokens.colors.button.cancel.bg,
                color: designTokens.colors.button.cancel.text
              }}
              className="hover:bg-btn-cancel-hover transition-colors"
            >
              Déconnexion
            </Button>
          </>
        )}
      </div>
    </aside>
  );
};

export default Sidebar;