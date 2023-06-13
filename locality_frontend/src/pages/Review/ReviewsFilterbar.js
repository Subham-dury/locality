import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import AddReviewModal from "../../components/modals/AddReviewModal";

function ReviewsFilterbar({ name }) {
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
          <button className="button button-dark" data-bs-toggle="modal" data-bs-target="#staticBackdrop">Add new review</button>
        )}
      </div>
      <AddReviewModal/>
    </div>
  );
}

export default ReviewsFilterbar;
