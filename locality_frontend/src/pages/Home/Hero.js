import React from "react";
import { useNavigate } from "react-router-dom";

const Hero = () => {
  const navigate = useNavigate()

  const direct = () => {
    navigate("/login", {
      state : {
        previousUrl: '/reviews',
      }
    });
  }

  return (
    <section className="hero d-flex align-items-center text-center">
      <div className="container">
        <div className="row">
          <div className="col-12">
            <h1>Find Your Next Perfect Place To Live</h1>
            <p className="mt-3 mb-4 lh-md">
              Not sure about your new loaclity, <strong>locality</strong> is here to help.
            </p>
            <div>
              <button className="button button-primary mx-3 my-2" type="button" onClick={direct}>
                Get started
              </button>
              <a href="#about">
                <button className="button button-light mx-3 my-2">About us</button>
              </a>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default Hero;
