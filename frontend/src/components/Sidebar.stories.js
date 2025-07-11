import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import Sidebar from './Sidebar';

// Configuration de Storybook pour le composant
export default {
  title: 'Components/Sidebar',
  component: Sidebar,
  decorators: [
    (Story) => (
      <BrowserRouter>
        <div style={{ height: '100vh' }}>
          <Story />
        </div>
      </BrowserRouter>
    ),
  ],
  parameters: {
    layout: 'fullscreen',
  },
};

// Story par défaut - version simplifiée sans mocks
export const Default = () => <Sidebar />;
