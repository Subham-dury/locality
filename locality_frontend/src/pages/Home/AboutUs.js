import React from "react";
import about from "../../assests/about.png";

const AboutUs = () => {
  return (
    <section className="about pt-4" id="about">
      <div className="container">
        <div className="row align-items-center justify-content-around">
          <div className="col-lg-4 info" >
            <h2>Welcome to locality</h2>
            <p>
              Choosing a new home is one of the biggest decisions of our lives.
              But before that comes the decision of choosing the perfect
              locality. What is it really like to live there?
              
            </p>
            <p>
              The only people who actually know are the people who live there.
            </p>
            <p>
              locality collects reviews and reports for neighbourhood
              across the country, so you can make an informed decision.
            </p>
            
          </div>
          <div className="col-lg-8 img-holder">
            <img src={about} alt="About us"/>
          </div>
        </div>
      </div>
    </section>
  );
};

export default AboutUs;
