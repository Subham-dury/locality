import { CATEGORIES_URL } from "../Config";

export const getEventTypeList = async () => {
  const response = await fetch(`${CATEGORIES_URL}/type/all`);
  const data = await response.json();
  return data;
};

export const saveEventTypeItem = async (eventType) => {
    try {
      const response = await fetch(`${CATEGORIES_URL}/type/`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: localStorage.getItem("token"),
        },
        body: JSON.stringify({typeOfEvent : eventType}),
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

export const updateEventTypeItem = async (eventType, typeId) => {
  try {
    const response = await fetch(`${CATEGORIES_URL}/type/${typeId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: localStorage.getItem("token"),
      },
      body: JSON.stringify({typeOfEvent : eventType}),
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

export const deleteEventType = async (typeId) => {
  try {
    const response = await fetch(`${CATEGORIES_URL}/type/${typeId}`, {
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
