import React from 'react'

const EventTypeCard = ({typeItem}) => {
  return (
    <div className="card my-3">
      <div className="row g-0">
         <div className="col-lg-7">
          <div className="card-body">
            <h6>{typeItem.typeOfEvent}</h6>
          </div>
        </div>
      </div>
    </div>
  )
}

export default EventTypeCard