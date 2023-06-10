import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";

function Filterbar({ name }) {
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
        {isSignedIn && (
          <button className="button button-dark">Add new review</button>
        )}
      </div>
    </div>
  );
}

export default Filterbar;
