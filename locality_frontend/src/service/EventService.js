import { REVIEWS_EVENTS_URL} from "../Config";

export const getAllEvents = async () => {
  try {
    const response = await fetch(`${REVIEWS_EVENTS_URL}/event/all`);
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

export const getEventByLocality = async (localityId) => {
  try {
    const response = await fetch(`${REVIEWS_EVENTS_URL}/event/bylocality/${localityId}`);
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

export const getEventByEventType = async (eventTypeId) => {
  try {
    const response = await fetch(`${REVIEWS_EVENTS_URL}/event/bytype/${eventTypeId}`);
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

export const getEventByLocalityAndEventType = async (localityId, eventTypeId) => {
  try {
    const response = await fetch(`${REVIEWS_EVENTS_URL}/event/bylocality/${localityId}/bytype/${eventTypeId}`);
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
