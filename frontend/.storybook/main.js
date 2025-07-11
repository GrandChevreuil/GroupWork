

/** @type { import('@storybook/react-webpack5').StorybookConfig } */
const config = {
  "stories": [
    "../src/**/*.stories.@(js|jsx|mjs|ts|tsx)"
  ],
  "addons": [
    "@storybook/preset-create-react-app",
    "@storybook/addon-docs"
  ],
  "framework": {
    "name": "@storybook/react-webpack5",
    "options": {
      "builder": {
        "useSWC": true
      }
    }
  },
  "staticDirs": [
    "..\\public"
  ],
  // Utilisation de la version 17 de React pour éviter les erreurs avec useId
  "core": {
    "disableTelemetry": true
  }
};
export default config;