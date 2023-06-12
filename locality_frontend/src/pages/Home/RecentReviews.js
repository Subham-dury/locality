import React from "react";
import { useNavigate } from "react-router-dom";
import { reviews } from "../../Data/RecentReviews";
import DetailsCard from "../../components/DetailsCard";

const RecentReviews = () => {

  const navigate = useNavigate();

  const direct = () => {
    navigate("/reviews");
  }

  return (
    <section className="featured-data featuredreviews py-5" id="featuredreviews">
      <div className="container">
        <div className="row heading align-items-center justify-content-evenly">
          <div className="col-6 col-md-8">
            <h3>Recent reviews</h3>
          </div>
          <div className="col-4 d-flex justify-content-end justify-content-xs-left">
            <button className="button button-primary" type="button" onClick={direct}>
              All reviews
            </button>
          </div>
        </div>
        <div
          id="reviewscarousel"
          className="carousel slide "
          data-bs-ride="carousel"
        >
          <div className="carousel-inner justify-content-center p-4">
            {reviews.map((review) => {
              return (
                <div
                  className={`carousel-item ${review.id === 1 ? "active" : ""}`}
                  key={review.id}
                >
                  <div className="d-flex justify-content-center">
                    <DetailsCard item={review} />
                  </div>
                </div>
              );
            })}
          </div>
          <button
            className="carousel-control-prev "
            type="button"
            data-bs-target="#reviewscarousel"
            data-bs-slide="prev"
          >
            <span
              className="carousel-control-prev-icon carousel-control"
              aria-hidden="true"
            ></span>
            <span className="visually-hidden">Previous</span>
          </button>
          <button
            className="carousel-control-next"
            type="button"
            data-bs-target="#reviewscarousel"
            data-bs-slide="next"
          >
            <span
              className="carousel-control-next-icon carousel-control"
              aria-hidden="true"
            ></span>
            <span className="visually-hidden">Next</span>
          </button>
        </div>
      </div>
    </section>
  );
};

export default RecentReviews;
