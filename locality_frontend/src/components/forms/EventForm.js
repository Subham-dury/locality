import React from "react";
import { localitylist } from "../../Data/LocalityList";
import { eventTypes } from "../../Data/EventTypeList";

const EventForm = ({
  setSelectedOption,
  selectedOption,
  setSelectedType,
  selectedtType,
  errMsg,
  updateEvent,
  eventDate,
  setEventDate,
}) => {
  return (
    <form>
      <div class="form-group my-4 text-start">
        <label htmlFor="selectedOption">Select locality</label>
        <select
          className="form-select"
          value={selectedOption}
          id="selectedOption"
          onChange={(e) => setSelectedOption(e.target.value)}
          required
        >
          {localitylist.map((locality) => {
            return (
              <option key={locality.localityId} value={locality.localityId}>
                {locality.name}
              </option>
            );
          })}
        </select>
      </div>
      <div class="form-group my-4 text-start">
        <label htmlFor="selectedOption">Select event type</label>
        <select
          className="form-select"
          value={selectedtType}
          onChange={(e) => setSelectedType(e.target.value)}
        >
          {eventTypes.map((type) => {
            return (
              <option key={type.eventTypeId} value={type.eventTypeId}>
                {type.typeOfEvent}
              </option>
            );
          })}
        </select>
      </div>
      <div class="form-group my-4 text-start">
        <label>
          Select event date:
          <input
            type="date"
            value={eventDate}
            onChange={(e) => setEventDate(e.target.value)}
          />
        </label>
      </div>
      <div class="form-group my-4 text-start">
        <label htmlFor="review">Add event description</label>
        <textarea
          class="form-control"
          id="exampleFormControlTextarea1"
          rows="3"
          onChange={(e) => updateEvent(e.target.value)}
        ></textarea>
        <small style={{ color: "red", fontSize: "0.8rem" }}>{errMsg}</small>
      </div>
    </form>
  );
};

export default EventForm;
