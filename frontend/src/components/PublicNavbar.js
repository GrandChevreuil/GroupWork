import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { Text, Group, Button } from "@mantine/core";
import { designTokens } from "../theme/designTokens";

/**
 * Barre de navigation pour les utilisateurs non authentifiés
 * @author Mathis Mauprivez
 */
const PublicNavbar = () => {
  const navigate = useNavigate();

  return (
    <header className="bg-white shadow-md py-4">
      <div className="container mx-auto px-4">
        <Group justify="space-between" align="center">
          <Link to="/" className="no-underline">
            <Text 
              component="span" 
              fw={700} 
              size="xl"
              style={{ color: designTokens.colors.text.h1, fontFamily: designTokens.typography.fontFamily.heading }}
            >
              GROUPWORK
            </Text>
          </Link>

          <Group>
            <Button
              variant="outline"
              onClick={() => navigate("/register")}
              sx={{
                borderColor: designTokens.colors.button.validation.bg,
                color: '#000000',
              }}
            >
              Sign up
            </Button>
            
            <Button
              variant="filled"
              onClick={() => navigate("/login")}
              sx={{
                backgroundColor: designTokens.colors.button.validation.bg,
                color: designTokens.colors.button.validation.text,
              }}
            >
              Login
            </Button>
          </Group>
        </Group>
      </div>
    </header>
  );
};

export default PublicNavbar;
