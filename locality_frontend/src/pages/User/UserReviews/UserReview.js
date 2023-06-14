import React, { useState, useEffect } from "react";
import SelectLocality from "../../../components/selectors/LocalitySelector";
import { localitylist } from "../../../Data/LocalityList";
import {
  getReviewByUser,
  getReviewByUserAndLocality,
} from "../../../service/ReviewService";
import UserReviewsContainer from "./UserReviewsContainer";
// import './UserReview.css'

const UserReview = () => {
  const [localityitem, setLocalityitem] = useState({});
  const [isLocalityListLoaded, setIsLocalityListLoaded] = useState(false);

  const [reviews, setReviews] = useState([]);
  const [errorInReview, setErrorInReview] = useState(null);

  const refresh = () => {
    updateReviewsToAll()
  }

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

    getReviewByUser()
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
    getReviewByUserAndLocality(option)
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
    <section className="user-reviev">
    <div className="container">
      <div className="row my-4 justify-content-center">
        <SelectLocality localitylist={localitylist} setLocality={setOption} />
      </div>
      <UserReviewsContainer reviews={reviews} />
    </div>
  </section>
  );
};

export default UserReview;
