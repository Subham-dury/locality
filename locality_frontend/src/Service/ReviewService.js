import { REVIEWS_EVENTS_URL} from "../Config";

export const getAllReview = async () => {
  try {
    const response = await fetch(`${REVIEWS_EVENTS_URL}/review/all`);
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

export const getReviewByLocality = async (localityId) => {
  try {
    const response = await fetch(`${REVIEWS_EVENTS_URL}/review/bylocality/${localityId}`);
    if (!response.ok) {
      const data = await response.json();
      throw new Error(data.message);
    }
    const data = await response.json();
    return data;
  } catch (error) {
    throw new Error(error.message);
  }
}

