/**
 * Configuration Tailwind CSS conforme à la charte graphique GroupWork
 * @author Mathis Mauprivez
 */

module.exports = {
  content: [
    './src/**/*.{js,jsx,ts,tsx}'
  ],
  theme: {
    extend: {
      colors: {
        // Couleurs de texte
        text: {
          h1: '#5c768a',
          h2: '#2c3d4a',
          h3: '#193143',
          body: '#193143',
          comment: '#87939c',
        },
        // Couleurs des boutons
        btn: {
          // Validation
          validation: {
            DEFAULT: '#81dcc9',
            hover: '#bdeee3',
            text: '#000000',
          },
          // Suppression/Annulation
          cancel: {
            DEFAULT: '#ff5757',
            hover: '#ff8f8f',
            text: '#000000',
          },
          // Générateur/Modifier/Retour
          action: {
            DEFAULT: '#acb8be',
            hover: '#4d585c',
            text: '#000000',
          },
          // Incrémenteur/Décrémenteur/Ajout/Agrandissement
          transparent: {
            DEFAULT: 'transparent',
            text: '#000000',
            iconLight: '#e6e6e6',
            iconHover: '#ffffff',
          },
        },
        // Couleurs de conteneurs
        container: {
          // Principal
          primary: {
            border: '#304d5c',
            bg: '#ffffff',
            content: '#304d5c',
          },
          // Secondaire
          secondary: {
            border: '#304d5c',
            bg: '#ffffff',
            bgClosed: '#304d5c',
            content: '#ffffff',
          },
          // Tertiaire
          tertiary: {
            border: '#acb8be',
            borderFaded: 'rgba(172, 184, 190, 0.5)',
            bg: '#ffffff',
            bgClosed: '#acb8be',
            content: '#ffffff',
          },
          // Quaternaire
          quaternary: {
            border: '#e6e6e6',
            bg: '#ffffff',
            content: '#193143',
          },
          // Formulaires
          form: {
            border: '#f5f5f5',
            bg: '#f5f5f5',
            content: '#193143',
          },
          // Badge
          badge: {
            border: '#81dcc9',
            bg: '#81dcc9',
            content: '#000000',
          },
        },
        // États (tâches, priorités)
        status: {
          // Tâches
          task: {
            todo: '#acb8be',
            inProgress: '#5c768a',
            done: '#81dcc9',
          },
          // Priorités
          priority: {
            hot: '#ff5757',
            warm: '#f2c94c',
            neutral: '#acb8be',
            cool: '#5c768a',
            frozen: '#81dcc9',
          },
          // Validation
          validation: {
            valid: '#81dcc9',
            invalid: '#e6e6e6',
          },
        },
      },
      fontFamily: {
        sans: ['Inter', 'sans-serif'],
        heading: ['Poppins', 'sans-serif'],
      },
      borderColor: {
        DEFAULT: '#e6e6e6',
      },
      backgroundColor: {
        page: '#ffffff',
        card: '#ffffff',
        form: '#f5f5f5',
      },
    },
  },
  plugins: [],
};