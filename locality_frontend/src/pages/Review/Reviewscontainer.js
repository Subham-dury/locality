import React from "react";
import ReviewCard from "../../components/Cards/ReviewCard";

function Reviewscontainer({ reviews }) {
  return (
    <div className="reviews-container">
      {reviews.map((review) => {
        return (
          <div key={review.reviewId} className="my-4">
            <div className="d-flex justify-content-center">
              <ReviewCard item={review} />
            </div>
          </div>
        );
      })}
    </div>
  );
}

export default Reviewscontainer;
