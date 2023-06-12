import { CATEGORIES_URL } from "../Config";

export const getLocalityList = async () => {
  const response = await fetch(`${CATEGORIES_URL}/locality/all`);
  const data = await response.json();
  return data;
};

export const removeLocalityFromList = async (localityId, user) => {
  const response = await fetch(`${CATEGORIES_URL}/locality/${localityId}`, {
    method: "DELETE",
    headers: { "Content-Type": "application/json", userId: user.userId },
  });
  const data = await response.json();
  return destructureCart(data);
};

