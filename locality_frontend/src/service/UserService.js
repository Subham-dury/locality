import { USERS_URL } from "../Config";

export const registerUser = async (username, email, password ) => {

  console.log({username, email, password})
  try {
    const response = await fetch(`${USERS_URL}/user/register`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, email, password }),
    });

    if (!response.ok) {
      const data = await response.json();
      throw new Error(data.message);
    }

    localStorage.setItem("token", response.headers.get("Authorization"))
    const data = await response.json();
    console.log(data);
    return data;
  } catch (error) {
    console.log(error);
    throw new Error(error.message);
  }
};

export const loginUser = async (user, password , isEmail) => {

  try {
    const response = await fetch(`${USERS_URL}/user/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json"},
      body: isEmail ? JSON.stringify({ email : user, password}) : JSON.stringify({username : user, password}),
    });

    if (!response.ok) {
      const data = await response.json();
      throw new Error(data.message);
    }

    localStorage.setItem("token", response.headers.get("Authorization"))
    const data = await response.json();
    return data;
  } catch (error) {
    console.log(error);
    throw new Error(error.message);
  }
};
