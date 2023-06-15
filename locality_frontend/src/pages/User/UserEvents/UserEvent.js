import React, { useState, useEffect } from "react";
import UserEventsContainer from "./UserEventsContainer";
import DataNotFoundCard from "../../../components/cards/DataNotFoundCard";
import { getEventByUser, deleteEvent } from "../../../service/EventService";
import "./UserEvent.css";

const UserEvent = () => {

  const [events, setEvents] = useState([]);
  const [errorInEvents, setErrorInEvents] = useState(null);

  useEffect(() => {
    updateEventsToAll();
  }, []);

  const refresh = () => {
    updateEventsToAll();
  }

  const updateEventsToAll = () => {
    getEventByUser()
      .then((data) => {
        setEvents(data);
        setErrorInEvents(null);
      })
      .catch((error) => {
        setEvents([]);
        setErrorInEvents(error.message);
      });
  };

  const deleteAEvent = (id) => {
    deleteEvent(id)
      .then((data) => {
        updateEventsToAll();
        console.log(data);
        setErrorInEvents(null);
      })
      .catch((error) => {
        setEvents([]);
        setErrorInEvents(error.message);
      });
  };


  return (
    <section className="user-event">
      <div className="container">
        {events.length != 0 && (
          <>
            <UserEventsContainer
              events={events}
              deleteAEvent={deleteAEvent}
              refresh={refresh}
            />
          </>
        )}
      </div>

      {events.length == 0 && (
        <div
          className="row my-4 justify-content-center"
          style={{ height: "70vh" }}
        >
          <DataNotFoundCard message={"Events not found"} />
        </div>
      )}

      
    </section>
  );
};

export default UserEvent;
