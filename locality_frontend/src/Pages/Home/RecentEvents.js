import React from "react";
import { useNavigate } from "react-router-dom";
import  {events} from '../../Data/RecentEvents'
import DetailsCard from "../../components/DetailsCard";

const RecentEvents = () => {

  const navigate = useNavigate();

  const direct = () => {
    navigate("/events");
  }

  return (
    <section
      className="featured-data featuredreports py-5"
      id="featuredreports"
    >
      <div className="container">
        <div className="row heading align-items-center justify-content-evenly">
          <div className="col-6 col-md-8">
            <h3>Recent events</h3>
          </div>
          <div className="col-4 d-flex justify-content-end justify-content-xs-left">
            <button className="button button-primary" type="button" onClick={direct}>
              All events
            </button>
          </div>
        </div>
        <div
          id="reportscarousel"
          className="carousel slide my-3"
          data-bs-ride="carousel"
        >
          <div className="carousel-inner justify-content-center p-4">
            {events.map((event) => {
              return (
                <div
                  className={`carousel-item ${event.id === 1 ? "active" : ""}`}
                  key={event.id}
                >
                  <div className="d-flex justify-content-center">
                    <DetailsCard item={event} />
                  </div>
                </div>
              );
            })}
          </div>
          <button
            className="carousel-control-prev "
            type="button"
            data-bs-target="#reportscarousel"
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
            data-bs-target="#reportscarousel"
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
};

export default RecentEvents;
