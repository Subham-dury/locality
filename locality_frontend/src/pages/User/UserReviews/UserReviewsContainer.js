import React from "react";
import ReviewCard from "../../../components/cards/ReviewCard";


const UserReviewsContainer = ({ reviews }) => {
  return (
    <div class="user-reviews-container">
      <div class="row row-cols-xxl-2">
        {reviews.map((review) => {
        return (
          <div key={review.reviewId} className="my-4">
            <div className="details-container">
              <ReviewCard item={review} />
              <div className="btn-group" role="group" aria-label="Basic example">
                <button type="button" className="button button-dark my-2">
                  Edit
                </button>
                <button type="button" className="button button-primary my-2">
                  Delete
                </button>
              </div>
            </div>
          </div>
        );
      })}
      </div>
    </div>
  );
};

export default UserReviewsContainer;
