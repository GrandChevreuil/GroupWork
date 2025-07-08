import './index.css';                           // Move your Tailwind reset first
import '@mantine/core/styles.css';              // Then Mantine styles
import '@mantine/notifications/styles.css';
import '@mantine/carousel/styles.css';
import '@mantine/dropzone/styles.css';
import '@mantine/dates/styles.css';
import '@mantine/code-highlight/styles.css';

import ReactDOM from 'react-dom';
import { BrowserRouter } from "react-router-dom";
import { MantineProvider } from '@mantine/core';
import { Notifications } from '@mantine/notifications';

import App from './App';
import { theme } from './theme';

ReactDOM.render(
  <BrowserRouter>
    <MantineProvider theme={theme} defaultColorScheme="light">
      <Notifications position="top-right" zIndex={2077} />
      <App />
    </MantineProvider>
  </BrowserRouter>,
  document.getElementById('root')
);
