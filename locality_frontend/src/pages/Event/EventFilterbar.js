import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import EventTypeSelector from "../../components/selectors/EventTypeSelector";

const EventFilterbar = ({name, setEventType, eventTypes}) => {

    const [isSignedIn, setIsSignedIn] = useState(false);
    const location = useLocation();
  
    useEffect(() => {
      setIsSignedIn(sessionStorage.getItem("isLoggedIn") ? true : false);
    }, [location]);

  return (
    <div className="filterbar text-center mx-auto">
    <div>
      <h3 className="ms-md-4">{name}</h3>
    </div>
    <div className="filterbuttons">
        <EventTypeSelector eventTypes={eventTypes} setEventType={setEventType} />
      {isSignedIn && (
        <button className="button button-dark">Add new event</button>
      )}
    </div>
  </div>
  )
}

export default EventFilterbar