import React from "react";

function LocalityNotFoundCard() {
  return (
    <section className="custom-section">
      <div className="custom-card">
        <div>
          <h5 className="card-text">
            Locality not selected, showing all reviews. Please select the
            locality above
          </h5>
        </div>
      </div>
    </section>
  );
}

export default LocalityNotFoundCard;
