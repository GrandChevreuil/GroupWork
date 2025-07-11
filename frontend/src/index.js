import './index.css';                           // Move your Tailwind reset first
import '@mantine/core/styles.css';              // Then Mantine styles
import '@mantine/notifications/styles.css';
import '@mantine/carousel/styles.css';
import '@mantine/dropzone/styles.css';
import '@mantine/dates/styles.css';
import '@mantine/code-highlight/styles.css';
import './theme/fonts.css';                    // Importation des polices personnalisées

import { createRoot } from 'react-dom/client';
import { BrowserRouter } from "react-router-dom";
import { MantineProvider } from '@mantine/core';
import { Notifications } from '@mantine/notifications';

import App from './App';
import { theme } from './theme';

const container = document.getElementById('root');
const root = createRoot(container);

root.render(
  <BrowserRouter>
    <MantineProvider theme={theme} defaultColorScheme="light">
      <Notifications position="top-right" zIndex={2077} />
      <App />
    </MantineProvider>
  </BrowserRouter>
);
