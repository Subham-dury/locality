import React from 'react'
import EventCard from '../../components/Cards/EventCard';

const EventContainer = ({events}) => {
  return (
    <div className="events-container">
    {events.map((event) => {
      return (
        <div key={event.eventId} className="my-4">
          <div className="d-flex justify-content-center">
            <EventCard item={event} />
          </div>
        </div>
      );
    })}
  </div>
  )
}

export default EventContainer