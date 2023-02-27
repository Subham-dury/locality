import React from "react";

const FeatureCard = ({ item }) => {
  return (
    <div className="card my-3">
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
            <h5>{item.locality}</h5>
            <p>Posted by {item.author}</p>

            <small className="text-muted">{item.date}</small>
            <p className="card-text my-2">{item.text}</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FeatureCard;
