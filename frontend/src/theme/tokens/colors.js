/**
 * Couleurs de la charte graphique
 */

export const colors = {
  // Couleurs de texte
  text: {
    h1: '#5c768a',
    h2: '#2c3d4a',
    h3: '#1a2830', // Plus foncé que h2
    body: '#193143',
    comment: '#87939c',
  },
  
  // Couleurs de boutons
  button: {
    validation: {
      bg: '#81dcc9',
      bgHover: '#bdeee3',
      text: '#000000',
    },
    cancel: {
      bg: '#ff5757',
      bgHover: '#ff8f8f',
      text: '#000000',
    },
    increment: {
      bg: 'transparent',
      text: '#000000',
    },
    add: {
      bg: 'transparent',
      text: 'inherit',
      textHover: '#000000',
    },
    generator: {
      bg: '#acb8be',
      bgHover: '#4d585c',
      text: '#000000',
    },
    edit: {
      bg: '#acb8be',
      bgHover: '#4d585c',
      text: '#000000',
    },
    back: {
      bg: '#acb8be',
      bgHover: '#4d585c',
      text: '#000000',
    },
    expander: {
      bg: 'transparent',
      text: '#e6e6e6',
      textHover: '#ffffff',
    }
  },
  
  // Couleurs de conteneurs
  container: {
    app: {
      bg: '#ffffff',
    },
    primary: {
      border: {
        closed: '#304d5c',
        open: 'transparent',
      },
      content: '#304d5c',
      bg: {
        closed: '#ffffff',
        open: '#ffffff',
      },
    },
    secondary: {
      border: {
        closed: '#304d5c',
        open: 'transparent',
      },
      content: '#ffffff',
      bg: {
        closed: '#304d5c',
        open: '#ffffff',
      },
    },
    tertiary: {
      border: {
        closed: '#acb8be',
        open: 'rgba(172, 184, 190, 0.5)', // #acb8be avec transparence 50%
      },
      content: '#ffffff',
      bg: {
        closed: '#acb8be',
        open: '#ffffff',
      },
    },
    quaternary: {
      border: '#e6e6e6',
      content: '#193143',
      bg: '#ffffff',
    },
    editor: {
      border: '#f5f5f5',
      content: '#193143',
      bg: '#f5f5f5',
    },
    badge: {
      border: '#81dcc9',
      content: '#000000',
      bg: '#81dcc9',
    }
  },
  
  // Couleurs d'états
  status: {
    task: {
      todo: '#acb8be',
      inProgress: '#f2c94c',
      done: '#81dcc9',
    },
    priority: {
      hot: '#ff5757',
      warm: '#f2994a',
      neutral: '#f2c94c',
      cool: '#81dcc9',
      frozen: '#5c768a',
    },
    validation: {
      valid: '#81dcc9',
      invalid: '#ff5757',
      noData: '#acb8be',
    }
  }
};
