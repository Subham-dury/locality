import React from "react";
import DetailsCard from "../../components/DetailsCard";

function Reviewscontainer({reviews}) {


  return (
    <div className="reviews-container">
      {reviews.map((review) => {
        return (
          <div key={review.id} className="my-4">
            <div className="d-flex justify-content-center">
              <DetailsCard item={review} />
            </div>
          </div>
        );
      })}
    </div>
  );
}

export default Reviewscontainer;
