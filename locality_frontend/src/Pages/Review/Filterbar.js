import React from "react";

function Filterbar() {
  return (
    <div className="filterbar text-center">
      <div>
        <h3>Reviews for locality a</h3>
      </div>
      <div className="filterbuttons">
        <button className="button button-primary">sort</button>
        <button className="button button-dark">Add new review</button>
      </div>
      <div></div>
    </div>
  );
}

export default Filterbar;
