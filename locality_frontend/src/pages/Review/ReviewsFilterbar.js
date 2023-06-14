import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import AddReviewModal from "../../components/modals/AddReviewModal";

function ReviewsFilterbar({ name, refresh }) {
  const [isSignedIn, setIsSignedIn] = useState(false);
  const [show, setShow] = useState(true)

  const location = useLocation();

  useEffect(() => {
    setIsSignedIn(localStorage.getItem("token") ? true : false);
  }, [location]);

  const toggleShow = () =>{
    setShow((prevValue) => !prevValue)
    refresh()
  }

  return (
    <div className="filterbar text-center mx-auto">
      <div>
        <h3 className="ms-md-4">{name}</h3>
      </div>
      <div className="filterbuttons">
        {isSignedIn && (
          <button
            className="button button-dark"
            data-bs-toggle="modal"
            data-bs-target="#staticBackdrop"
            onCick={toggleShow}
          >
            Add new review
          </button>
        )}
      </div>
      {show && <AddReviewModal toggleShow={toggleShow}/>}
    </div>
  );
}

export default ReviewsFilterbar;
