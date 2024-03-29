import React from "react";
import "./Card.css";

const ReviewCard = ({ item }) => {

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
            <h9>{item.localityname}</h9>
            <br/>
            <p className="card-text mb-4">{item.content}</p>
            <small className="text-muted">Posted on {item.date}</small>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ReviewCard;
