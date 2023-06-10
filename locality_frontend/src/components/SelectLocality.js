import React, { useState } from "react";

function SelectLocality({ localitylist, setlocality }) {
  const [selectedOption, setSelectedOption] = useState(0);

  const setOption = (e) => {
    setSelectedOption(e.target.value);
    setlocality(e.target.value);
  };

  return (
    <div className="selectlocality">
      <div>
        <h3>Select locality </h3>
      </div>
      <div>
        <select
          className="form-select"
          value={selectedOption}
          onChange={setOption}
        >
          <option value={0} selected >
            All
          </option>
          {localitylist.map((locality) => {
            return (
              <option key={locality.localityId} value={locality.localityId}>
                {locality.name}
              </option>
            );
          })}
        </select>
      </div>
    </div>
  );
}

export default SelectLocality;
