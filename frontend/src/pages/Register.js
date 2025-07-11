import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import {
  Anchor,
  Button,
  Paper,
  PasswordInput,
  Text,
  TextInput,
  Title,
  Alert,
} from '@mantine/core';
import { IconAlertCircle, IconAt, IconLock, IconUserCircle } from '@tabler/icons-react';
import { isEmail } from "validator";
import AuthService from "../services/auth.service";
import { useAppNotifications } from "../hooks/useAppNotifications";
import { designTokens } from "../theme/designTokens";
import classes from "../styles/auth/Login.module.css";

const Register = () => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [successful, setSuccessful] = useState(false);
  const [message, setMessage] = useState("");

  const notifications = useAppNotifications();
  const navigate = useNavigate();

  // Validations
  const validateUsername = (value) => {
    if (!value.trim()) return "Le nom d'utilisateur est requis";
    if (value.length < 3 || value.length > 20) return "Le nom d'utilisateur doit contenir entre 3 et 20 caractères";
    return null;
  };

  const validateEmail = (value) => {
    if (!value.trim()) return "L'email est requis";
    if (!isEmail(value)) return "Format d'email invalide";
    return null;
  };

  const validatePassword = (value) => {
    if (!value.trim()) return "Le mot de passe est requis";
    if (value.length < 6 || value.length > 40) return "Le mot de passe doit contenir entre 6 et 40 caractères";
    return null;
  };

  const handleRegister = (e) => {
    e.preventDefault();

    // Validation
    const usernameError = validateUsername(username);
    const emailError = validateEmail(email);
    const passwordError = validatePassword(password);

    if (usernameError || emailError || passwordError) {
      const errorMessage = usernameError || emailError || passwordError;
      notifications.showError(errorMessage);
      return;
    }

    setMessage("");
    setLoading(true);
    setSuccessful(false);

    AuthService.register(username, email, password).then(
      (response) => {
        setMessage(response.data.message || "Inscription réussie! Vous pouvez maintenant vous connecter.");
        setSuccessful(true);
        setLoading(false);
        notifications.showSuccess("Inscription réussie!");

        // Rediriger vers la page de login après 2 secondes
        setTimeout(() => {
          navigate("/login");
        }, 2000);
      },
      (error) => {
        const resMessage =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();

        setMessage(resMessage);
        setSuccessful(false);
        setLoading(false);
        notifications.showError("Erreur d'inscription");
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
          Créer un compte
        </Title>

        {!successful ? (
          <form onSubmit={handleRegister}>
            <TextInput
              label="Nom d'utilisateur"
              placeholder="Votre nom d'utilisateur"
              leftSection={<IconUserCircle size={16} color="#87939c" />}
              size="md"
              radius="md"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              error={validateUsername(username) && username !== ""}
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

            <TextInput
              label="Email"
              placeholder="Votre adresse email"
              leftSection={<IconAt size={16} color="#87939c" />}
              mt="md"
              size="md"
              radius="md"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              error={validateEmail(email) && email !== ""}
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
              error={validatePassword(password) && password !== ""}
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

            {message && !successful && (
              <Alert
                icon={<IconAlertCircle size="1rem" />}
                title="Erreur d'inscription"
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
              S'inscrire
            </Button>

            <Text ta="center" mt="xl" style={{
              color: designTokens.colors.text.comment,
              fontFamily: designTokens.typography.fontFamily.body
            }}>
              Vous avez déjà un compte ?{' '}
              <Anchor
                component={Link}
                to="/login"
                fw={500}
                style={{ color: designTokens.colors.text.h2 }}
              >
                Se connecter
              </Anchor>
            </Text>
          </form>
        ) : (
          <Alert
            icon={<IconAlertCircle size="1rem" />}
            title="Inscription réussie !"
            color="green"
            mt="md"
          >
            {message}
            <Text mt="md">Vous allez être redirigé vers la page de connexion...</Text>
          </Alert>
        )}
      </Paper>
    </div>
  );
};

export default Register;
