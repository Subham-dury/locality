import React from "react";

function Localityinfo() {
  return (
    <>
      <div className="col-lg-7">
        <h3>locality-A</h3>
        <p className="text-mute">17k zcouookn roadclknbinisdclklk</p>
        <p>
          Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ullam at
          ipsa minus veniam soluta libero, voluptate deleniti provident fugiat
          consequatur.
        </p>
      </div>
      <div className=" col-lg-5 text-center">
        <img
          src={require(`../../assests/card-holder-3.jpg`)}
          alt="locality-a"
          className="img-fluid"
        />
      </div>
    </>
  );
}

export default Localityinfo;
