import React from "react";

const FeatureCard = ({ review }) => {
  return (
    <div class="card m-3">
      <div class="row g-0">
        <div class="col-lg-5">
          <div className="card-img">
            <img
              src={require(`../../assests/review-holder-${review.img}.jpg`)}
              class="img-fluid rounded"
              alt="locality image"
            />
          </div>
        </div>
        <div class="col-lg-7">
          <div class="card-body">
            <h5>{review.locality}</h5>
            <p>Review by {review.author}</p>

            <small class="text-muted">{review.date}</small>
            <p class="card-text my-2">{review.text}</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FeatureCard;
