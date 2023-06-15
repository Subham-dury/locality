import React, { useState, useEffect } from "react";
import EventForm from "../forms/EventForm";
import { saveEvent } from "../../service/EventService";

const AddEventModal = ({ handleCloseModal }) => {
  const [event, setEvent] = useState("");
  const [errMsg, setErrMsg] = useState("");
  const [selectedOption, setSelectedOption] = useState(1);
  const [selectedtType, setSelectedType] = useState(1);
  const [eventDate, setEventDate] = useState("");

  useEffect(() => {
    setErrMsg("");
  }, [event, eventDate]);

  const updateEvent = (value) => {
    setEvent(value);
  };

  const handle = () => {
    if (validateEvent() && validateDate()) {
      save();
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

  const save = () => {
    saveEvent(selectedOption, selectedtType, eventDate, event)
      .then((data) => {
        console.log(data);
        closeModal();
        handleCloseModal();
      })
      .catch((error) => {
        console.log(error);
        setErrMsg(error.message);
      });
  };

  function closeModal() {
    document.getElementById("staticBackdrop").classList.remove("show");
    document
      .querySelectorAll(".modal-backdrop")
      .forEach((el) => el.classList.remove("modal-backdrop"));
  }

  return (
    <div
      className="modal fade"
      id="staticBackdrop"
      data-bs-backdrop="static"
      data-bs-keyboard="false"
      tabIndex="-1"
      aria-labelledby="staticBackdropLabel"
      aria-hidden="true"
    >
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="staticBackdropLabel">
              Add an event
            </h5>
            <button
              type="button"
              className="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>

          <div className="modal-body">
            <EventForm
              setSelectedOption={setSelectedOption}
              selectedOption={selectedOption}
              setSelectedType={setSelectedType}
              selectedtType={selectedtType}
              errMsg={errMsg}
              updateEvent={updateEvent}
              eventDate={eventDate}
              setEventDate={setEventDate}
            />
          </div>
          <div className="modal-footer">
            <button
              type="button"
              className="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Close
            </button>
            <button type="button" className="btn btn-primary" onClick={handle}>
              Submit
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddEventModal;
