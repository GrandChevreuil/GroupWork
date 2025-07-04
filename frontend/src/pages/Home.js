import React, { useState, useEffect } from "react";

import UserService from "../services/user.service";

const Home = () => {
  const [content, setContent] = useState("");

  useEffect(() => {
    UserService.getPublicContent().then(
      (response) => {
        setContent(response.data);
      },
      (error) => {
        const _content =
          (error.response && error.response.data) ||
          error.message ||
          error.toString();

        setContent(_content);
      }
    );
  }, []);

  return (
    <div className="container">
      <header className="jumbotron">
        <h3>{content}</h3>
        <h1>Welcome to the Groupwork Application</h1>
        <p>
          This application is designed to facilitate group work and collaboration.
          You can register, log in, and access various features based on your role.
        </p>
        <p>
          If you are a new user, please <a href="/register">register</a> to get started.
        </p>
        <p>
          If you already have an account, please <a href="/login">log in</a> to access your dashboard.
        </p>
        <p>
          For more information, please refer to the documentation or contact support.
        </p>
        <p>
          Enjoy using the Groupwork Application!
        </p>
      </header>
    </div>
  );
};

export default Home;
