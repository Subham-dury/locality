import React from 'react'
import './Card.css'

function DataNotFoundCard({message}) {
  return (
    <section className="custom-section">
      <div className="custom-card">
        <div>
          <h5 className="card-text">{message}</h5>
          <p className="card-text">Oops! The requested data could not be found.</p>
        </div>
      </div>
    </section>
  );
}

export default DataNotFoundCard