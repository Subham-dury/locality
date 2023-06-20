import React, { useEffect, useState } from "react";
import EventCard from "../../../components/cards/EventCard";
import EditEventModal from "../../../components/modals/EditEventModal";
import { updateEvent } from "../../../service/EventService";


const UserEventsContainer = ({ events, deleteAEvent, refresh }) => {

  const [show, setShow] = useState(false);
  const [eid, setEid] = useState("");
  const [errMsg, setErrMsg] = useState("");
  const [event, setEvent] = useState("");
  const [eventDate, setEventDate] = useState("");

  const handleDeleteReview = (eventId) => {
    deleteAEvent(eventId);
  };

  useEffect(() => {
    setErrMsg("");
  }, [event, eventDate]);

  const handle = () => {
    if (validateEvent() && validateDate()) {
      update();
      console.log("no error");
    }
  };

  const validateEvent = () => {
    if (!(event.length > 9 && event.length < 256)) {
      setErrMsg("Review must have 10 to 255 characters");
      return false;
    }
    return true;
  };

  const validateDate = () => {
    const selectedDate = new Date(eventDate);
    const currentDate = new Date();

    if (!(selectedDate < currentDate)) {
      setErrMsg("Event date needs to be older than today");
      return false;
    }
    return true;
  };

  const update = () => {
    updateEvent(eid, eventDate, event)
      .then((data) => {
        closeModal();
        handleCloseModal();
      })
      .catch((error) => {
        console.log(error);
        setErrMsg(error.message);
      });
  };

  useEffect(() => {
    handleOpenModal();
  }, [show]);

  const handleOpenModal = () => {
    setShow(true);
  };

  const handleCloseModal = () => {
    setShow(false);
    refresh();
  };

  function closeModal() {
    document.getElementById("staticBackdrop").classList.remove("show");
    document
      .querySelectorAll(".modal-backdrop")
      .forEach((el) => el.classList.remove("modal-backdrop"));
  }
  return (
    <>
    <div class="user-events-container">
      <div class="row row-cols-xxl-2">
        {events.map((event) => {
          return (
            <div key={event.eventId} className="my-4">
              <div className="details-container">
                <EventCard item={event} />
                <div
                  className="btn-group"
                  role="group"
                  aria-label="Basic example"
                >
                  <button
                    type="button"
                    className="button button-dark my-2"
                    onClick={() => {
                      handleOpenModal();
                      setEid(event.eventId);
                    }}
                    data-bs-toggle="modal"
                    data-bs-target="#staticBackdrop"
                  >
                    Edit
                  </button>
                  <button
                    type="button"
                    className="button button-primary my-2"
                    onClick={() => handleDeleteReview(event.eventId)}
                  >
                    Delete
                  </button>
                </div>
              </div>
            </div>
          );
        })}
      </div>
      
    </div>
    {show && (
      <EditEventModal
        handle={handle}
        errMsg={errMsg}
        eventDate={eventDate}
        setEvent={setEvent}
        setEventDate={setEventDate}
      />
    )}
    </>
  );
};

export default UserEventsContainer;
