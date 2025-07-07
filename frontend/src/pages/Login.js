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
import { IconAlertCircle } from '@tabler/icons-react';
import AuthService from "../services/auth.service";
import { useAppNotifications } from "../hooks/useAppNotifications";
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
      <Paper className={classes.form}>
        <Title order={2} className={classes.title}>
          Bienvenue sur GroupWork
        </Title>

        <form onSubmit={handleLogin}>
          <TextInput
            label="Nom d'utilisateur"
            placeholder="Votre nom d'utilisateur"
            size="md"
            radius="md"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />

          <PasswordInput
            label="Mot de passe"
            placeholder="Votre mot de passe"
            mt="md"
            size="md"
            radius="md"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />

          <Checkbox
            label="Se souvenir de moi"
            mt="xl"
            size="md"
            checked={rememberMe}
            onChange={(e) => setRememberMe(e.currentTarget.checked)}
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
            mt="xl"
            size="md"
            radius="md"
            type="submit"
            loading={loading}
          >
            Se connecter
          </Button>

          <Text ta="center" mt="md">
            Pas encore de compte ?{' '}
            <Anchor component={Link} to="/register" fw={500}>
              S'inscrire
            </Anchor>
          </Text>
        </form>
      </Paper>
    </div>
  );
};

export default Login;
