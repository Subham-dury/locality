import React from "react";
import SelectLocality from "./SelectLocality";
import Localityinfo from "./Localityinfo";
import Filterbar from "./Filterbar";
import { reviews } from "../../Data/FeaturedReviews";
import "./Review.css";
import Reviewscontainer from "./Reviewscontainer";

const Review = () => {
  return (
    <section className="reviev">
      <div className="container">
        <div className="row my-3 justify-content-center">
          <SelectLocality />
        </div>
        <div className="row localitydesc">
          <Localityinfo />
        </div>
        <div className="row my-3 mx-1">
          <Filterbar />
        </div>
        <div className="row my-3">
          <Reviewscontainer reviews={reviews}/>
        </div>
      </div>
    </section>
  );
};

export default Review;
