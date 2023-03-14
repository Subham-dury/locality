import React from "react";

function SelectLocality() {
  return (
    <div className="selectlocality">
      <div>
        <h3>Select locality</h3>
      </div>
      <div>
        <select class="form-select" aria-label="Default select example">
          <option selected>--- Select locality ---</option>
          <option value="1">One</option>
          <option value="2">Two</option>
          <option value="3">Three</option>
        </select>
      </div>
    </div>
  );
}

export default SelectLocality;
