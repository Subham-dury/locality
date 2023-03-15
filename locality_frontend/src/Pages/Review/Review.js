import React, { useState, useEffect } from "react";
import SelectLocality from "../../components/SelectLocality";
import Localityinfo from "../../components/Localityinfo";
import Filterbar from "./ReviewsFilterbar";
import Reviewscontainer from "./Reviewscontainer";
import { localitylist } from "../../Data/LocalityList";
import { reviews } from "../../Data/RecentReviews";
import "./Review.css";
import NoLocality from "../../components/NoLocality";

const Review = () => {
  const [localityitem, setLocalityitem] = useState({});
  const [isLocalityListLoaded, setIsLocalityListLoaded] = useState(false);

  const setOption = (option) => {
    setLocalityitem(
      localitylist.filter((locality) => locality.id == option)[0]
    );
    setIsLocalityListLoaded(true);
  };

  return (
    <section className="reviev">
      <div className="container">
        <div className="row my-4 justify-content-center">
          <SelectLocality localitylist={localitylist} setlocality={setOption} />
        </div>
        {isLocalityListLoaded && (
          <div className="row localitydesc my-4">
            <Localityinfo localityitem={localityitem} />
          </div>
        )}
        {!isLocalityListLoaded && <NoLocality />}
        <div className="row my-3 mx-1">
          <Filterbar
            name={
              isLocalityListLoaded
                ? `reviews for ${localityitem.name}`
                : `All reviews`
            }
          />
        </div>
        <div className="row my-3">
          <Reviewscontainer reviews={reviews} />
        </div>
      </div>
    </section>
  );
};

export default Review;
