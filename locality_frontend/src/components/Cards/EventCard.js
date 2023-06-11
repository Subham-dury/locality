import React from "react";
import "./DetailsCard.css";

const EventCard = ({ item }) => {
  return (
      <div className="card my-3 featurecard">
      <div className="row g-0">
        <div className="col-lg-5">
          <div className="card-img">
            <img
              src={require(`../../assests/card-holder-${item.img}.jpg`)}
              className="img-fluid rounded-start"
              alt="locality"
            />
          </div>
        </div>
        <div className="col-lg-7">
          <div className="card-body">
            <h6>Posted by {item.username} </h6>
            <p>{item.localityname}</p>
            <span className="card-text me-2 text-muted">
              Date : {item.eventDate}
            </span>

            <br />

            <span className="card-text ms-2 text-muted">
              About : {item.eventType}
            </span>
            <p className="card-text mb-4">{item.content}</p>
            <small className="text-muted">Posted on {item.postDate}</small>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EventCard;
