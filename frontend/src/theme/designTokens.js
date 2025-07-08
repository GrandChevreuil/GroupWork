/**
 * Design Tokens - Charte graphique GroupWork
 * Définit l'ensemble des valeurs de style pour assurer la cohérence de l'interface
 * @author Mathis Mauprivez
 */

export const designTokens = {
  // Polices
  typography: {
    fontFamily: {
      heading: 'Poppins, sans-serif', // Police de titres
      body: 'Inter, sans-serif', // Police de texte
    },
  },
  
  // Couleurs
  colors: {
    // Couleurs textuelles
    text: {
      h1: '#5c768a',       // Titres de pages h1
      h2: '#2c3d4a',       // Titre h2
      h3: '#193143',       // Titre h3 (plus foncé)
      body: '#193143',     // Corps de texte
      comment: '#87939c',  // Commentaires
    },
    
    // Couleurs des boutons
    button: {
      // Boutons de validation
      validation: {
        bg: '#81dcc9', // #81dcc9
        bgHover: '#bdeee3', // #bdeee3
        text: '#000000', // #000000
        text2:'#81dcc9',
      },
      // Boutons de suppression/annulation
      cancel: {
        bg: '#ff5757', // #ff5757
        bgHover: '#ff8f8f', // #ff8f8f
        text: '#000000',
      },
      // Boutons incrémenteurs/décrémenteurs
      increment: {
        bg: 'transparent',
        text: '#000000',
      },
      // Boutons d'ajout
      add: {
        bg: 'transparent',
        text: 'inherit', // Prend la couleur du conteneur parent
        textHover: '#000000',
      },
      // Boutons générateur de groupes
      generator: {
        bg: '#acb8be',
        bgHover: '#4d585c',
        text: '#000000',
      },
      // Boutons de modification
      edit: {
        bg: '#acb8be', // #acb8be
        bgHover: '#4d585c',// #4d585c
        text: '#000000',
      },
      // Boutons de retour
      back: {
        bg: '#acb8be',
        bgHover: '#4d585c',
        text: '#000000',
      },
      // Boutons d'agrandissement
      expand: {
        bg: 'transparent',
        text: '#e6e6e6',
        textHover: '#ffffff',
      },
    },
    
    // Couleurs des conteneurs
    container: {
      // Application GroupWork
      app: {
        bg: '#ffffff',
      },
      // Conteneur principal
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
      // Conteneur secondaire
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
      // Conteneur tertiaire
      tertiary: {
        border: {
          closed: '#acb8be',
          open: 'rgba(172, 184, 190, 0.5)', // #acb8be avec 50% transparence
        },
        content: '#ffffff',
        bg: {
          closed: '#acb8be',
          open: '#ffffff',
        },
      },
      // Conteneur quaternaire / div
      quaternary: {
        border: '#e6e6e6',
        content: '#193143',
        bg: '#ffffff',
      },
      // Editeur, Formulaire
      form: {
        border: '#f5f5f5',
        content: '#193143',
        bg: '#f5f5f5',
      },
      // Badge
      badge: {
        border: '#81dcc9',
        content: '#000000',
        bg: '#81dcc9',
      },
    },
    
    // États (tâches, priorités)
    status: {
      // États de tâches
      task: {
        todo: '#acb8be',       // À faire
        inProgress: '#5c768a', // En cours
        done: '#81dcc9',       // Terminée
      },
      // États de priorités
      priority: {
        hot: '#ff5757',        // Chaud
        warm: '#f2c94c',       // Tiède
        neutral: '#acb8be',    // Neutre
        cool: '#5c768a',       // Froid
        frozen: '#81dcc9',     // Gelé
      },
      // États de validation
      validation: {
        valid: '#81dcc9',      // Validé
        invalid: '#e6e6e6',    // Non validé / pas de données
      },
    },
  },
};
