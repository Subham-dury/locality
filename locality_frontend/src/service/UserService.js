import { USERS_URL } from "../Config";

export const registerUser = async ({ username, email, password }) => {
    const response = await fetch(`${BACKEND_TWO_API_URL}/register`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, email, password }),
    });
  
    const data = await response.json();
  
    if (!response.ok) {
      const error = new Error("Email registered");
      if (data.message === "Email already registered") {
        error.props = {
          email: "Email already registered",
        };
      }
      throw error;
    }
    return {
      token: data.token,
      user: {
        userId: data.userId,
        userName: data.userName,
        token: data.token,
      },
    };
  };