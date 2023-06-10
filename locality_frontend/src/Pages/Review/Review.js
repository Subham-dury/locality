import React, { useState, useEffect } from "react";
import SelectLocality from "../../components/SelectLocality";
import Localityinfo from "../../components/Localityinfo";
import Filterbar from "./ReviewsFilterbar";
import Reviewscontainer from "./Reviewscontainer";
import LocalityNotFound from "../../components/LocalityNotFound";
import { localitylist } from "../../Data/LocalityList";
import { reviews } from "../../Data/ReviewsList";
import "./Review.css";

const Review = () => {
  const [localityitem, setLocalityitem] = useState({});
  const [isLocalityListLoaded, setIsLocalityListLoaded] = useState(false);

  const setOption = (option) => {
    if (option != "0") {
      setLocalityitem(
        localitylist.filter((locality) => locality.id == option)[0]
      );
      setIsLocalityListLoaded(true);
    } else {
      setIsLocalityListLoaded(false);
    }
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
        {!isLocalityListLoaded && <LocalityNotFound />}
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
