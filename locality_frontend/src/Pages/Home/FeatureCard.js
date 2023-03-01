import React from "react";

const FeatureCard = ({ item }) => {
  return (
    <div className="card my-3 featurecard">
      <div className="row g-0">
        <div className="col-lg-5">
          <div className="card-img">
            <img
              src={require(`../../assests/card-holder-${item.img}.jpg`)}
              className="img-fluid rounded"
              alt="locality"
            />
          </div>
        </div>
        <div className="col-lg-7">
          <div className="card-body">
            <h6>Posted by {item.author} </h6>
            <p>Place : {item.locality}</p>
            {item.dateOfEvent && (
              <span className="card-text me-2 text-muted">Event date : {item.dateOfEvent}</span>
            )}
            {item.type && <span className="card-text ms-2 text-muted">About : {item.type}</span>}
            <p className="card-text mb-4">{item.text}</p>
            <small className="text-muted">Posted on {item.date}</small>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FeatureCard;
