import React from "react";

const LocalityCard = ({ localityitem }) => {
  return (
    <div className="card my-3 featurecard">
      <div className="row g-0">
        <div className="col-lg-5">
          <div className="card-img">
            <img
              src={require(`../../assests/card-holder-${
                localityitem.img ? localityitem.img : 1
              }.jpg`)}
              className="img-fluid rounded-start"
              alt="locality"
            />
          </div>
        </div>
        <div className="col-lg-7">
          <div className="card-body">
            <h3>{localityitem.name}</h3>
            <span className="text-mute">{localityitem.city}</span>
            <span className="">{", "+localityitem.state}</span>
            <p>{localityitem.about}</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LocalityCard;
