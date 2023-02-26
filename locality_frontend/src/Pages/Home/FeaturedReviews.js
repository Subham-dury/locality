import React from "react";
import reviews from "../../Data/FeaturedReviews";
import FeatureCard from "./FeatureCard";

const FeaturedReviews = () => {
  return (
    <section className="featuredreviews p-5" id="featuredreviews">
      <div className="container">
        <h3>Featured Reviews </h3>
        <div
          id="carouselExampleControls"
          class="carousel slide"
          data-bs-ride="carousel"
        >
          <div class="carousel-inner justify-content-center">
            <div class="carousel-item active">
              <div className="d-flex justify-content-center">
                <FeatureCard />
              </div>
            </div>
            <div class="carousel-item">
              <div className="d-flex justify-content-center">
                <FeatureCard />
              </div>
            </div>
            <div class="carousel-item">
              <div className="d-flex justify-content-center">
                <FeatureCard />
              </div>
            </div>
          </div>
          <button
            class="carousel-control-prev"
            type="button"
            data-bs-target="#carouselExampleControls"
            data-bs-slide="prev"
          >
            <span class="carousel-control-prev-icon carousal-control" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
          </button>
          <button
            class="carousel-control-next"
            type="button"
            data-bs-target="#carouselExampleControls"
            data-bs-slide="next"
          >
            <span class="carousel-control-next-icon carousal-control" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
          </button>
        </div>

      </div>
    </section>
  );
};

export default FeaturedReviews;
