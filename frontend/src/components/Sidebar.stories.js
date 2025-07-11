import React from 'react';
import { MemoryRouter, Link } from 'react-router-dom';
import Sidebar from './Sidebar';

// Créer une version simplifiée du Sidebar spécifique pour Storybook
// qui ne dépend pas de useAppNotifications
import { Text, Button } from "@mantine/core";
import { IconHome, IconUser, IconShield, IconSettings, IconLogout, IconUserCircle } from '@tabler/icons-react';
import { designTokens } from "../theme/designTokens";

// Configuration de Storybook pour le composant
export default {
  title: 'Components/Sidebar',
  component: Sidebar,
  decorators: [
    (Story) => (
          <MemoryRouter>
              <Story />
          </MemoryRouter>
    ),
  ],
  parameters: {
    layout: 'fullscreen',
  },
};

// Composant de Sidebar spécifique pour Storybook
const SidebarForStory = ({ userRole = null }) => {
    // États simulés
    const showModeratorBoard = userRole === 'SUPERVISING_STAFF';
    const showAdminBoard = userRole === 'ADMIN_SYSTEM';
    const currentUser = userRole ? {
        username: userRole === 'ADMIN_SYSTEM' ? 'admin' : userRole === 'SUPERVISING_STAFF' ? 'moderator' : 'user',
        roles: [userRole]
    } : null;

    // Fonction de déconnexion simulée
    const handleLogout = () => {
        console.log('User logged out');
    };

    return (
        <aside className="fixed inset-y-0 left-0 w-64 shadow-lg flex flex-col" style={{
            backgroundColor: designTokens.colors.container.primary.border.closed,
            color: '#ffffff', // Texte blanc pour la sidebar
        }}>
            <div className="px-6 py-5" style={{
                borderBottom: `1px solid ${designTokens.colors.container.secondary.border.closed}`,
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

                {currentUser && userRole !== 'ADMIN_SYSTEM' && (
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
                borderTop: `1px solid ${designTokens.colors.container.secondary.border.closed}`,
            }}>
                {currentUser ? (
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
                ) : (
                    <>
                        <Link
                            to="/login"
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
                            Connexion
                        </Link>
                        <Link
                            to="/register"
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
                            S'inscrire
                        </Link>
                    </>
                )}
            </div>
        </aside>
    );
};

// Stories pour différents états d'utilisateur

export const LoggedInAsUser = () => <SidebarForStory userRole="USER" />;

export const LoggedInAsModerator = () => <SidebarForStory userRole="SUPERVISING_STAFF" />;

export const LoggedInAsAdmin = () => <SidebarForStory userRole="ADMIN_SYSTEM" />;
