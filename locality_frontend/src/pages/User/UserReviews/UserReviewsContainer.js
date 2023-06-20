import React, { useEffect, useState } from "react";
import ReviewCard from "../../../components/cards/ReviewCard";
import EditReviewModal from "../../../components/modals/EditReviewModal";
import { updateReview} from "../../../service/ReviewService";

import "./UserReview.css"

const UserReviewsContainer = ({ reviews, deleteAReview, refresh }) => {
  const [show, setShow] = useState(false);
  const [rid, setRid] = useState("")
  const [errMsg, setErrMsg] = useState("");
  const [review, setReview] = useState("");


  const handleDeleteReview = (reviewId) => {
    deleteAReview(reviewId);
  };

  useEffect(() => {
    setErrMsg("");
  }, [review]);

  const handle = () => {
    setErrMsg(
      !(review.length > 9 && review.length < 256)
        ? "Review must have 10 to 255 characters"
        : ""
    );

    if (review.length > 9 && review.length < 256) {
      update();
    }
  };

  const update = () => {

    updateReview(rid, review)
      .then((data) => {
        closeModal();
        handleCloseModal();
      })
      .catch((error) => {
        console.log(error);
        setErrMsg(error.message);
      });
  };

  useEffect(() => {
    handleOpenModal();
  }, [show]);

  const handleOpenModal = () => {
    setShow(true);
  };

  const handleCloseModal = () => {
    setShow(false);
    refresh();
  };

  function closeModal() {
    document.getElementById("staticBackdrop").classList.remove("show");
    document
      .querySelectorAll(".modal-backdrop")
      .forEach((el) => el.classList.remove("modal-backdrop"));
  }

  return (
    <div class="user-reviews-container">
      <div class="row row-cols-xxl-2">
        {reviews.map((review) => {
          return (
            <div key={review.reviewId}>
              <div className="details-container">
                <ReviewCard item={review} />
                <div
                  className="btn-group"
                  role="group"
                  aria-label="Basic example"
                >
                  <button
                    type="button"
                    className="button button-dark my-2"
                    onClick={() => {handleOpenModal(); setRid(review.reviewId)}}
                    data-bs-toggle="modal"
                    data-bs-target="#staticBackdrop"
                  >
                    Edit
                  </button>
                  <button
                    type="button"
                    className="button button-primary my-2"
                    onClick={() => handleDeleteReview(review.reviewId)}
                  >
                    Delete
                  </button>
                </div>
              </div>
            </div>
          );
        })}
      </div>
      {show && <EditReviewModal handle={handle} errMsg={errMsg} setReview={setReview}/>}
    </div>
  );
};

export default UserReviewsContainer;
