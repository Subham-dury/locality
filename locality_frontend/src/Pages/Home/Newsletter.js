import React from "react";

const Newsletter = () => {
  return (
    <section className="p-5 newsletter-section">
      <div className="container">
        <div className="d-md-flex justify-content-evenly align-items-center">
          <h5 className="mb-3 mb-md-0">Sign up for newsletter</h5>
          <div className="input-group news-input">
            <input
              type="text"
              className="form-control"
              placeholder="Enter email"
              aria-label="User's email"
              aria-describedby="button-addon2"
            />
            <button
              className="button button-dark"
              type="button"
            >
              Sign Up
            </button>
          </div>
        </div>
      </div>
    </section>
  );
};

export default Newsletter;
