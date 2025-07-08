import { createTheme } from '@mantine/core';
import { designTokens } from './theme/designTokens';

/**
 * Thème global Mantine
 * Adapté à la charte graphique du projet GroupWork
 * @author Mathis Mauprivez
 */
export const theme = createTheme({
  primaryColor: 'teal',
  primaryShade: 6,
  colors: {
    // Couleurs personnalisées pour Mantine
    teal: [
      '#f0faf8',
      '#d0f0ea',
      '#bdeee3',
      '#a0e5d6',
      '#81dcc9',
      '#5ed0ba',
      '#46c5ac',
      '#35a892',
      '#2a8a79',
      '#1c6557',
    ],
    gray: [
      '#f5f5f5',
      '#e6e6e6',
      '#d1d5d8',
      '#acb8be',
      '#87939c',
      '#5c768a',
      '#304d5c',
      '#2c3d4a',
      '#193143',
      '#1a2830',
    ],
    red: [
      '#fff0f0',
      '#ffe0e0',
      '#ffd0d0',
      '#ffc0c0',
      '#ffb0b0',
      '#ffa0a0',
      '#ff8f8f',
      '#ff7878',
      '#ff6767',
      '#ff5757',
    ]
  },
  
  // Polices
  fontFamily: {
    default: designTokens.typography.fontFamily.body,
    heading: designTokens.typography.fontFamily.heading,
  },
  
  // Configuration des composants
  components: {
    Title: {
      defaultProps: {
        fw: 600,
      },
      styles: {
        root: (theme) => ({
          fontFamily: designTokens.typography.fontFamily.heading,
          
          // Applique les couleurs en fonction du niveau du titre
          '&[data-order="1"]': {
            color: designTokens.colors.text.h1,
          },
          
          '&[data-order="2"]': {
            color: designTokens.colors.text.h2,
          },
          
          '&[data-order="3"]': {
            color: designTokens.colors.text.h3,
          },
        }),
      },
    },
    Text: {
      styles: {
        root: (theme) => ({
          color: designTokens.colors.text.body,
          '&.muted': {
            color: designTokens.colors.text.comment,
          },
        }),
      },
    },
    Button: {
      variants: {
        validation: {
          root: (theme) => ({
            backgroundColor: designTokens.colors.button.validation.bg,
            color: designTokens.colors.button.validation.text,
            '&:hover': {
              backgroundColor: designTokens.colors.button.validation.bgHover,
            },
          }),
        },
        cancel: {
          root: (theme) => ({
            backgroundColor: designTokens.colors.button.cancel.bg,
            color: designTokens.colors.button.cancel.text,
            '&:hover': {
              backgroundColor: designTokens.colors.button.cancel.bgHover,
            },
          }),
        },
        edit: {
          root: (theme) => ({
            backgroundColor: designTokens.colors.button.edit.bg,
            color: designTokens.colors.button.edit.text,
            '&:hover': {
              backgroundColor: designTokens.colors.button.edit.bgHover,
            },
          }),
        },
        back: {
          root: (theme) => ({
            backgroundColor: designTokens.colors.button.back.bg,
            color: designTokens.colors.button.back.text,
            '&:hover': {
              backgroundColor: designTokens.colors.button.back.bgHover,
            },
          }),
        },
        generator: {
          root: (theme) => ({
            backgroundColor: designTokens.colors.button.generator.bg,
            color: designTokens.colors.button.generator.text,
            '&:hover': {
              backgroundColor: designTokens.colors.button.generator.bgHover,
            },
          }),
        },
      },
    },
    Card: {
      styles: {
        root: (theme) => ({
          backgroundColor: designTokens.colors.container.quaternary.bg,
          borderColor: designTokens.colors.container.quaternary.border,
        }),
      },
    },
    Badge: {
      defaultProps: {
        color: 'teal',
      },
      styles: {
        root: (theme) => ({
          backgroundColor: designTokens.colors.container.badge.bg,
          color: designTokens.colors.container.badge.content,
          borderColor: designTokens.colors.container.badge.border,
        }),
      },
    },
    Input: {
      styles: {
        input: (theme) => ({
          backgroundColor: designTokens.colors.container.form.bg,
          borderColor: designTokens.colors.container.form.border,
          color: designTokens.colors.container.form.content,
        }),
      },
    },
  },
});
