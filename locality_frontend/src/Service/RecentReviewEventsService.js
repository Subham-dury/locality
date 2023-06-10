import { REVIEWS_EVENTS_URL } from "../Config";

export const getRecentReview = async () => {
  try {
    const response = await fetch(`${REVIEWS_EVENTS_URL}/review/recent`);
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

export const getRecentEvent = async () => {
  try {
    const response = await fetch(`${REVIEWS_EVENTS_URL}/event/recent`);
    
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
