/** @type { import('@storybook/react-webpack5').Preview } */
// Import des styles globaux pour que Tailwind fonctionne dans Storybook
import React from 'react';
import '../src/index.css';
import { MantineProvider } from '@mantine/core';
import { Notifications } from '@mantine/notifications';

// Décorateur pour ajouter MantineProvider à toutes les stories
const withMantine = (Story) => {
  return (
    <MantineProvider defaultColorScheme="light">
      <Notifications position="top-right" />
      <Story />
    </MantineProvider>
  );
};

// Paramètres globaux pour toutes les stories
const preview = {
  decorators: [withMantine],
  parameters: {
    actions: { argTypesRegex: '^on[A-Z].*' },
    controls: {
      matchers: {
        color: /(background|color)$/i,
        date: /Date$/i,
      },
    },
    // Configuration des fonds pour les stories
    backgrounds: {
      default: 'light',
      values: [
        { name: 'light', value: '#f8fafc' },
        { name: 'dark', value: '#1f2937' },
      ],
    },
  },
};

export default preview;