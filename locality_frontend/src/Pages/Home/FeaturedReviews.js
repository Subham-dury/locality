import React from "react";
import { reviews } from "../../Data/FeaturedReviews";
import FeatureCard from "./FeatureCard";

const FeaturedReviews = () => {
  return (
    <section className="featuredreviews py-5" id="featuredreviews">
      <div className="container">
        <div className="row heading align-items-center justify-content-evenly">
          <div className="col-6 col-md-8">
            <h3>Featured reviews</h3>
          </div>
          <div className="col-4 d-flex justify-content-end justify-content-xs-left">
            <button class="button button-primary" type="button">
              Learn more
            </button>
          </div>
        </div>
        <div
          id="carouselExampleControls"
          class="carousel slide"
          data-bs-ride="carousel"
        >
          <div class="carousel-inner justify-content-center">
            {reviews.map((review) => {
              return (
                <div
                  class={`carousel-item ${review.id == 1 ? "active" : ""}`}
                  key={review.id}
                >
                  <div className="d-flex justify-content-center">
                    <FeatureCard review={review} />
                  </div>
                </div>
              );
            })}
          </div>
          <button
            class="carousel-control-prev "
            type="button"
            data-bs-target="#carouselExampleControls"
            data-bs-slide="prev"
          >
            <span
              class="carousel-control-prev-icon carousel-control"
              aria-hidden="true"
            ></span>
            <span class="visually-hidden">Previous</span>
          </button>
          <button
            class="carousel-control-next"
            type="button"
            data-bs-target="#carouselExampleControls"
            data-bs-slide="next"
          >
            <span
              class="carousel-control-next-icon carousel-control"
              aria-hidden="true"
            ></span>
            <span class="visually-hidden">Next</span>
          </button>
        </div>
      </div>
    </section>
  );
};

export default FeaturedReviews;
