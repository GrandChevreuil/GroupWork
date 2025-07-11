/** @type { import('@storybook/react-webpack5').Preview } */
// Import des styles globaux pour que Tailwind fonctionne dans Storybook
import '../src/index.css';

// Paramètres globaux pour toutes les stories
const preview = {
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