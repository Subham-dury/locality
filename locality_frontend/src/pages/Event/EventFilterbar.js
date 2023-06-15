import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import EventTypeSelector from "../../components/selectors/EventTypeSelector";
import AddEventModal from "../../components/modals/AddEventModal";

const EventFilterbar = ({name, setEventType, eventTypes, refresh}) => {

  const [isSignedIn, setIsSignedIn] = useState(false);
  const [show, setShow] = useState(false)

  const location = useLocation();

  useEffect(() => {
    setIsSignedIn(localStorage.getItem("token") ? true : false);
  }, [location]);

  useEffect(() => {
    handleOpenModal();
  }, [show])

  const handleOpenModal = () => {
    setShow(true);
  };

  const handleCloseModal = () => {
    setShow(false);
    refresh()
  };

  return (
    <div className="filterbar text-center mx-auto">
    <div>
      <h3 className="ms-md-4">{name}</h3>
    </div>
    <div className="filterbuttons">
        <EventTypeSelector eventTypes={eventTypes} setEventType={setEventType} />
      {isSignedIn && (
        <button className="button button-dark" onClick={handleOpenModal} data-bs-toggle="modal" data-bs-target="#staticBackdrop">Add new event</button>
      )}
    </div>
    {show && <AddEventModal handleCloseModal={handleCloseModal}/>}
  </div>
  )
}

export default EventFilterbar