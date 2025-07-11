import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import {
  Anchor,
  Button,
  Checkbox,
  Paper,
  PasswordInput,
  Text,
  TextInput,
  Title,
  Alert,
} from '@mantine/core';
import { IconAlertCircle, IconAt, IconLock } from '@tabler/icons-react';
import AuthService from "../services/auth.service";
import { useAppNotifications } from "../hooks/useAppNotifications";
import { designTokens } from "../theme/designTokens";
import classes from "../styles/auth/Login.module.css";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const [rememberMe, setRememberMe] = useState(false);

  const notifications = useAppNotifications();
  const navigate = useNavigate();

  const handleLogin = (e) => {
    e.preventDefault();

    // Validation basique
    if (!username.trim() || !password.trim()) {
      notifications.showError("Veuillez remplir tous les champs");
      return;
    }

    setMessage("");
    setLoading(true);

    AuthService.login(username, password).then(
      () => {
        notifications.showSuccess("Connexion réussie!");
        navigate("/profile");
        window.location.reload();
      },
      (error) => {
        const resMessage =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();

        setLoading(false);
        setMessage(resMessage);
        notifications.showError("Erreur de connexion");
      }
    );
  };

  return (
    <div className={classes.wrapper}>
      <Paper className={classes.form} style={{
        backgroundColor: designTokens.colors.container.form.bg,
        borderColor: designTokens.colors.container.form.border
      }}>
        <Title order={1} className={classes.title} style={{
          color: designTokens.colors.text.h1,
          fontFamily: designTokens.typography.fontFamily.heading,
          marginBottom: '1.5rem',
          textAlign: 'center'
        }}>
          Bienvenue sur GroupWork
        </Title>

        <form onSubmit={handleLogin}>
          <TextInput
            label="Nom d'utilisateur"
            placeholder="Votre nom d'utilisateur"
            leftSection={<IconAt size={16} color="#87939c" />}
            size="md"
            radius="md"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
            styles={{
              input: {
                backgroundColor: '#ffffff',
                borderColor: designTokens.colors.container.form.border,
                color: designTokens.colors.container.form.content,
              },
              label: {
                color: designTokens.colors.text.body,
                fontFamily: designTokens.typography.fontFamily.body,
                fontWeight: 500,
                marginBottom: '0.3rem'
              }
            }}
          />

          <PasswordInput
            label="Mot de passe"
            placeholder="Votre mot de passe"
            leftSection={<IconLock size={16} color="#87939c" />}
            mt="md"
            size="md"
            radius="md"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            styles={{
              input: {
                backgroundColor: '#ffffff',
                borderColor: designTokens.colors.container.form.border,
                color: designTokens.colors.container.form.content,
              },
              label: {
                color: designTokens.colors.text.body,
                fontFamily: designTokens.typography.fontFamily.body,
                fontWeight: 500,
                marginBottom: '0.3rem'
              }
            }}
          />

          <Checkbox
            label="Se souvenir de moi"
            mt="xl"
            size="md"
            checked={rememberMe}
            onChange={(e) => setRememberMe(e.currentTarget.checked)}
            styles={{
              label: {
                color: designTokens.colors.text.body,
                fontFamily: designTokens.typography.fontFamily.body
              }
            }}
          />

          {message && (
            <Alert
              icon={<IconAlertCircle size="1rem" />}
              title="Erreur d'authentification"
              color="red"
              mt="md"
            >
              {message}
            </Alert>
          )}

          <Button
            fullWidth
            variant="filled"
            mt="xl"
            size="md"
            radius="md"
            type="submit"
            loading={loading}
            sx={{
              backgroundColor: designTokens.colors.button.validation.bg,
              fontFamily: designTokens.typography.fontFamily.body,
              fontWeight: 500
            }}
            className="hover:bg-btn-validation-hover transition-colors"
          >
            Se connecter
          </Button>

          <Text ta="center" mt="xl" style={{
            color: designTokens.colors.text.comment,
            fontFamily: designTokens.typography.fontFamily.body
          }}>
            Pas encore de compte ?{' '}
            <Anchor
              component={Link}
              to="/register"
              fw={500}
              style={{ color: designTokens.colors.text.h2 }}
            >
              S'inscrire
            </Anchor>
          </Text>
        </form>
      </Paper>
    </div>
  );
};

export default Login;
