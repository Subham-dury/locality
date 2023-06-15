import React, { useState } from "react";

const EventTypeSelector = ({ eventTypes, setEventType }) => {
  
  const [selectedOption, setSelectedOption] = useState(0);

  const setOption = (e) => {
    setSelectedOption(e.target.value);
    setEventType(e.target.value);
  };

  return (
    <div>
      <select
        className="form-select"
        value={selectedOption}
        onChange={setOption}
      >
        <option value={0} selected>
          All
        </option>
        {eventTypes.map((type) => {
          return (
            <option key={type.eventTypeId} value={type.eventTypeId}>
              {type.typeOfEvent}
            </option>
          );
        })}
      </select>
    </div>
  );
};

export default EventTypeSelector;
