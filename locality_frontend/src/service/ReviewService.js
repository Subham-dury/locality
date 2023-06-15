import { REVIEWS_EVENTS_URL } from "../Config";

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
    const response = await fetch(
      `${REVIEWS_EVENTS_URL}/review/bylocality/${localityId}`
    );
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

export const getReviewByUser = async () => {
  try {
    const response = await fetch(`${REVIEWS_EVENTS_URL}/review/byuser`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
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

export const saveReview = async (localityId, review) => {
  try {
    const response = await fetch(
      `${REVIEWS_EVENTS_URL}/review/?localityId=${localityId}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: localStorage.getItem("token"),
        },
        body: JSON.stringify({ content: review }),
      }
    );
    if (!response.ok) {
      const data = await response.json();
      console.log(data.message);
      throw new Error(data.message);
    }
    const data = await response.json();
    return data;
  } catch (error) {
    throw new Error(error.message);
  }
};

export const updateReview = async (reviewId, review) => {
  try {
    const response = await fetch(
      `${REVIEWS_EVENTS_URL}/review/${reviewId}`,
      {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: localStorage.getItem("token"),
        },
        body: JSON.stringify({content : review}),
      }
    );
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

export const deleteReview = async (reviewId) => {
  try {
    const response = await fetch(`${REVIEWS_EVENTS_URL}/review/${reviewId}`, {
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
