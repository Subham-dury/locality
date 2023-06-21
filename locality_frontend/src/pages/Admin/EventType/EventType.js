import React, { useContext } from "react";
import DataContext from "../../../context/DataContext";
import EventTypeContainer from "./EventTypeContainer";
import EventTypeForm from "../../../components/forms/EventTypeForm";

const EventType = () => {
  const {eventTypelist} = useContext(DataContext)

  return (
    <section className="type-section">
      <div className="container mt-3">
      <h2>Add event type</h2>
        <EventTypeForm/>
      </div>
      <div className="container mt-3">
      <h2>Manage event types</h2>
        <EventTypeContainer eventTypelist={eventTypelist} />
      </div>
    </section>
  )
}

export default EventType