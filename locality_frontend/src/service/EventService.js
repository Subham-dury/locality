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

export const getEventByUser = async () => {
  try {
    const response = await fetch(`${REVIEWS_EVENTS_URL}/event/byuser`,{
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

export const saveEvent = async (localityId,eventTypeId, eventDate, event) => {
  try {
    const response = await fetch(
      `${REVIEWS_EVENTS_URL}/event/?localityId=${localityId}&typeId=${eventTypeId}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: localStorage.getItem("token"),
        },
        body: JSON.stringify({ eventDate : eventDate, content: event }),
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

export const updateEvent = async (eventId, event) => {
  try {
    const response = await fetch(
      `${REVIEWS_EVENTS_URL}/event/${eventId}`,
      {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: localStorage.getItem("token"),
        },
        body: JSON.stringify({content : event}),
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

export const deleteEvent = async (eventId) => {
  try {
    const response = await fetch(`${REVIEWS_EVENTS_URL}/event/${eventId}`, {
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
