import React, { useState, useEffect } from "react";
import SelectLocality from "../../components/SelectLocality";
import Localityinfo from "../../components/Cards/LocalityInfoCard";
import Filterbar from "./ReviewsFilterbar";
import Reviewscontainer from "./Reviewscontainer";
import LocalityNotFound from "../../components/Cards/LocalityNotFoundCard";
import { localitylist } from "../../Data/LocalityList";
import { getAllReview, getReviewByLocality } from "../../Service/ReviewService";
import "./Review.css";
import DataNotFoundCard from "../../components/Cards/DataNotFoundCard";

const Review = () => {
  const [localityitem, setLocalityitem] = useState({});
  const [isLocalityListLoaded, setIsLocalityListLoaded] = useState(false);

  const [reviews, setReviews] = useState([]);
  const [errorInReview, setErrorInReview] = useState(null);

  const setOption = (option) => {
    if (option != "0") {
      setLocalityitem(
        localitylist.filter((locality) => locality.localityId == option)[0]
      );
      setIsLocalityListLoaded(true);
      updateReviews(option)
    } else {
      setIsLocalityListLoaded(false);
    }
  };

  useEffect(() => {
    getAllReview()
      .then((data) => setReviews(data))
      .catch((error) => setErrorInReview(error.message));
  }, []);

  const updateReviews = (option) => {
    getReviewByLocality(option)

    .then(data => {
      setReviews(data)
      setErrorInReview(null)
    })
    .catch(error => {
      setReviews([])
      setErrorInReview(error.message);
    });;
  }



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
          {!errorInReview && <Reviewscontainer reviews={reviews}/>}
          {errorInReview && <DataNotFoundCard message={errorInReview}/>}
        </div>
      </div>
    </section>
  );
};

export default Review;
