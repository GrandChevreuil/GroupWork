import { useNotifications } from '@mantine/notifications';
import { IconCheck, IconX } from '@tabler/icons-react';

/**
 * Hook personnalisé pour gérer les notifications de l'application
 * @author Mathis Mauprivez
 */

export function useAppNotifications() {
  const notifications = useNotifications();

  return {
    /**
     * Affiche une notification de succès
     * @param {string} message - Le message à afficher
     * @param {string} title - Le titre de la notification (optionnel)
     */
    showSuccess: (message, title = 'Succès') => {
      notifications.show({
        title,
        message,
        color: 'green',
        icon: <IconCheck size="1.1rem" />,
        autoClose: 5000,
      });
    },
    
    /**
     * Affiche une notification d'erreur
     * @param {string} message - Le message à afficher
     * @param {string} title - Le titre de la notification (optionnel)
     */
    showError: (message, title = 'Erreur') => {
      notifications.show({
        title,
        message,
        color: 'red',
        icon: <IconX size="1.1rem" />,
        autoClose: 7000,
      });
    },
    
    /**
     * Affiche une notification d'information
     * @param {string} message - Le message à afficher
     * @param {string} title - Le titre de la notification (optionnel)
     */
    showInfo: (message, title = 'Information') => {
      notifications.show({
        title,
        message,
        color: 'blue',
        autoClose: 5000,
      });
    },
    
    /**
     * Affiche une notification d'avertissement
     * @param {string} message - Le message à afficher
     * @param {string} title - Le titre de la notification (optionnel)
     */
    showWarning: (message, title = 'Attention') => {
      notifications.show({
        title,
        message,
        color: 'yellow',
        autoClose: 6000,
      });
    },
    
    // Accès à l'API complète de notifications
    ...notifications
  };
}
