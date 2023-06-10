import React from "react";

function Localityinfo({ localityitem }) {
  return (
    <>
      <div className="col-lg-7">
        <h3>{localityitem.name}</h3>
        <p className="text-mute">{localityitem.city}</p>
        <p>{localityitem.about}</p>
      </div>
      <div className=" col-lg-5 text-center imgholder">
        <img
          src={require(`../../assests/card-holder-${
            localityitem.img ? localityitem.img : 1
          }.jpg`)}
          className="img-fluid"
        />
      </div>
    </>
  );
}

export default Localityinfo;
