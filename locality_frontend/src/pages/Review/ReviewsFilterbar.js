import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import AddReviewModal from "../../components/modals/AddReviewModal";

function ReviewsFilterbar({ name, refresh }) {
  const [isSignedIn, setIsSignedIn] = useState(false);
  const [show, setShow] = useState(false)

  const location = useLocation();

  useEffect(() => {
    setIsSignedIn(localStorage.getItem("token") ? true : false);
  }, [location]);

  useEffect(() => {
    handleOpenModal();
  }, [show])

  const handleOpenModal = () => {
    setShow(true);
  };

  const handleCloseModal = () => {
    setShow(false);
    refresh()
  };

  return (
    <>
     <div className="filterbar text-center mx-auto">
      <div>
        <h3 className="ms-md-4">{name}</h3>
      </div>
      <div className="filterbuttons">
        {isSignedIn && (
          <button className="button button-dark" onClick={handleOpenModal} data-bs-toggle="modal" data-bs-target="#staticBackdrop">Add new review</button>
        )}
      </div>
    </div>
    {show && <AddReviewModal handleCloseModal={handleCloseModal}/>}
    </>
   
  );
}

export default ReviewsFilterbar;
