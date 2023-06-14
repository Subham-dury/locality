import { CATEGORIES_URL } from "../Config";

export const getLocalityList = async () => {
  const response = await fetch(`${CATEGORIES_URL}/locality/all`);
  const data = await response.json();
  return data;
};

export const updateLocalityItem = async (locality, localityId) => {
  try {
    const reponse = await fetch(`${CATEGORIES_URL}/locality/${localityId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: localStorage.getItem("token"),
      },
      body: JSON.stringify(locality),
    });
    if (!response.ok) {
      const data = await response.json();
      throw new Error(data.message);
    }
    const data = await response.json();
    return data;
  } catch (error) {
    throw new Error(error.message);
  }
};

export const deleteLocality = async (localityId) => {
  try {
    const response = await fetch(`${CATEGORIES_URL}/locality/${localityId}`, {
      method: "DELETE",
      headers: {
        Authorization: localStorage.getItem("token"),
      },
    });
    if (!response.ok) {
      const data = await response.json();
      throw new Error(data.message);
    }
    const data = await response.json();
    return data;
  } catch (error) {
    throw new Error(error.message);
  }
};
