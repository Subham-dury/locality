import React from "react";

function Filterbar({name}) {
  return (
    <div className="filterbar text-center">
      <div>
        <h3>{name}</h3>
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
