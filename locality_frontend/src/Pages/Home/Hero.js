import React from "react";

const Hero = () => {
  return (
    <section className="hero d-flex align-items-center text-center">
      <div className="container">
        <div className="row">
          <div className="col-12">
            <h1>Find Your Next Perfect Place To Live</h1>
            <h5 className="mt-3 mb-4 lh-md">
              Moving to a new neighbourhood not sure if it's safe, not to worry
              locality is here to assist you.
            </h5>
            <div>
              <button className="button button-primary mx-3" type="button">
                Learn more
              </button>
              <a href="#about">
                <button className="button button-light mx-3">About us</button>
              </a>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default Hero;
