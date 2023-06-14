import React, { useState, useEffect } from "react";
import ReviewForm from "../forms/ReviewForm";
import { saveReview } from "../../service/ReviewService";

const AddReviewModal = ({handleCloseModal}) => {

  const [review, setReview] = useState("");
  const [errMsg, setErrMsg] = useState("");
  const [selectedOption, setSelectedOption] = useState(1);

  useEffect(() => {
    setErrMsg("");
  }, [review]);

  const updateSelectedOption = (value) => {
    setSelectedOption(value);
  };

  const updateReview = (value) => {
    setReview(value);
  };

  const handle = () => {
    setErrMsg(
      !(review.length > 9 && review.length < 256)
        ? "Review must have 10 to 255 characters"
        : ""
    );

    if (review.length > 9 && review.length < 256) {
      save()
    }
  };

  const save = () => {
    saveReview(selectedOption, review)
    .then((data) => {
      console.log(data)
      closeModal()
      handleCloseModal()
    })
    .catch((error) => {
      console.log(error);
      setErrMsg(error.message);
    });
  }

  function closeModal(){            
    document.getElementById("staticBackdrop").classList.remove("show");
    document.querySelectorAll(".modal-backdrop")
            .forEach(el => el.classList.remove("modal-backdrop"));    
}

  return (
    <div
      className="modal fade"
      id="staticBackdrop"
      data-bs-backdrop="static"
      data-bs-keyboard="false"
      tabIndex="-1"
      aria-labelledby="staticBackdropLabel"
      aria-hidden="true"
    >
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="staticBackdropLabel">
              Add a review
            </h5>
            <button
              type="button"
              className="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>

          <div className="modal-body">
            <ReviewForm
              updateSelectedOption={updateSelectedOption}
              selectedOption={selectedOption}
              updateReview={updateReview}
              errMsg={errMsg}
            />
          </div>
          <div className="modal-footer">
            <button
              type="button"
              className="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Close
            </button>
            <button type="button" className="btn btn-primary" onClick={handle}>
              Submit
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddReviewModal;
