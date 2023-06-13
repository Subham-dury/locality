import React from "react";
import { useNavigate } from "react-router-dom";
import ReviewCard from "../../components/cards/ReviewCard";
import EventCard from "../../components/cards/EventCard";

function RecentData({
  data,
  isReview,
  title,
  buttonVal,
  redirect,
  dataTarget,
}) {
  const navigate = useNavigate();
  const direct = () => {
    navigate(redirect);
  };

  return (
    <section
      className="featured-data featuredreviews py-5"
      id="featuredreviews"
    >
      <div className="container-xl">
        <div className="row heading align-items-center justify-content-evenly">
          <div className="col-6 col-md-8">
            <h3>{title}</h3>
          </div>
          <div className="col-4 d-flex justify-content-end justify-content-xs-left">
            <button
              className="button button-primary"
              type="button"
              onClick={direct}
            >
              {buttonVal}
            </button>
          </div>
        </div>
        <div
          id={dataTarget}
          className="carousel slide "
          data-bs-ride="carousel"
        >
          <div className="carousel-inner justify-content-center p-4">
            {isReview && <ReviewsViewer data={data} />}
            {!isReview && <EventsViewer data={data} />}
          </div>
          <button
            className="carousel-control-prev "
            type="button"
            data-bs-target={`#${dataTarget}`}
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
            data-bs-target={`#${dataTarget}`}
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
}

export default RecentData;

const ReviewsViewer = ({ data }) => {
  return (
    <>
      {data.map((d) => {
        return (
          <div
            className={`carousel-item ${
              d.reviewId === data[0].reviewId ? "active" : ""
            }`}
            key={d.reviewId}
          >
            <div className="d-flex justify-content-center">
              <ReviewCard item={d} />
            </div>
          </div>
        );
      })}
    </>
  );
};

const EventsViewer = ({ data }) => {
  return (
    <>
      {data.map((d) => {
        return (
          <div
            className={`carousel-item ${
              d.eventId === data[0].eventId ? "active" : ""
            }`}
            key={d.eventId}
          >
            <div className="d-flex justify-content-center">
              <EventCard item={d} />
            </div>
          </div>
        );
      })}
    </>
  );
};
