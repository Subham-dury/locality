import React, { useState, useEffect } from "react";
import SelectLocality from "../../../components/selectors/LocalitySelector";
import { localitylist } from "../../../Data/LocalityList";
import {
  getReviewByUser,
  deleteReview,
  updateReview,
} from "../../../service/ReviewService";
import UserReviewsContainer from "./UserReviewsContainer";
import DataNotFoundCard from "../../../components/cards/DataNotFoundCard";
import "./UserReview.css";

const UserReview = () => {

  const [reviews, setReviews] = useState([]);
  const [errorInReview, setErrorInReview] = useState(null);

  useEffect(() => {
    updateReviewsToAll();
  }, []);

  const refresh = () => {
    updateReviewsToAll();
  }

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

  const deleteAReview = (id) => {
    deleteReview(id)
      .then((data) => {
        updateReviewsToAll();
        console.log(data);
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
        {reviews.length != 0 && (
          <>
          <UserReviewsContainer
            reviews={reviews}
            deleteAReview={deleteAReview}
            refresh={refresh}
          />
          </>)
          }
        </div>
      
        {reviews.length == 0 && (
        <div className="row my-4 justify-content-center" style={{height: "70vh"}}>
          <DataNotFoundCard message={"Reviews not found"} />
        </div>
      )}
    </section>
  );
};

export default UserReview;
