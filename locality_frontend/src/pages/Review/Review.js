import React, { useState, useEffect } from "react";
import SelectLocality from "../../components/LocalitySelector";
import Localityinfo from "../../components/Cards/LocalityInfoCard";
import ReviewsFilterbar from "./ReviewsFilterbar";
import Reviewscontainer from "./Reviewscontainer";
import LocalityNotFound from "../../components/Cards/LocalityNotFoundCard";
import { localitylist } from "../../Data/LocalityList";
import { getAllReview, getReviewByLocality } from "../../service/ReviewService";
import "./Review.css";
import DataNotFoundCard from "../../components/Cards/DataNotFoundCard";

const Review = () => {
  const [localityitem, setLocalityitem] = useState({});
  const [isLocalityListLoaded, setIsLocalityListLoaded] = useState(false);

  const [reviews, setReviews] = useState([]);
  const [errorInReview, setErrorInReview] = useState(null);


  useEffect(() => {
    updateReviewsToAll();
  }, []);

  const setOption = (option) => {
    if (option != 0) {
      setLocalityitem(
        localitylist.filter((locality) => locality.localityId == option)[0]
      );
      setIsLocalityListLoaded(true);
      updateReviewsByOption(option);
    } else {
      setIsLocalityListLoaded(false);
      updateReviewsToAll();
    }
  };


  const updateReviewsToAll = () => {
    getAllReview()
    .then((data) => {
      setReviews(data);
      setErrorInReview(null);
    })
    .catch((error) => {
      setReviews([]);
      setErrorInReview(error.message);
    });
  };

  const updateReviewsByOption = (option) => {
    getReviewByLocality(option)
      .then((data) => {
        setReviews(data);
        setErrorInReview(null);
      })
      .catch((error) => {
        setReviews([]);
        setErrorInReview(error.message);
      });
  };

  return (
    <section className="reviev">
      <div className="container">
        <div className="row my-4 justify-content-center">
          <SelectLocality localitylist={localitylist} setLocality={setOption} />
        </div>
        {isLocalityListLoaded && (
          <div className="row localitydesc my-4">
            <Localityinfo localityitem={localityitem} />
          </div>
        )}
        {!isLocalityListLoaded && <LocalityNotFound />}
        <div className="row my-3 mx-1">
          <ReviewsFilterbar
            name={
              isLocalityListLoaded
                ? `reviews for ${localityitem.name}`
                : `All reviews`
            }
          />
        </div>
        <div className="row my-3">
          {!errorInReview && <Reviewscontainer reviews={reviews} />}
          {errorInReview && <DataNotFoundCard message={errorInReview} />}
        </div>
      </div>
    </section>
  );
};

export default Review;
