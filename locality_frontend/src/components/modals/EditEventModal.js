import React from "react";

const EditEventModal = ({handle, setEvent, setEventDate,eventDate, errMsg}) => {
  return (
    <div
      className="modal fade"
      id="staticBackdrop"
      data-bs-backdrop="static"
      data-bs-keyboard="false"
      tabIndex="-1"
      aria-labelledby="staticBackdropLabel"
      aria-hidden="true"
    >
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="staticBackdropLabel">
              Modify the event
            </h5>
            <button
              type="button"
              className="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>

          <div className="modal-body">
            <form>
              <div class="form-group my-4 text-start">
                <label>
                  Select event date:
                  <input
                    type="date"
                    value={eventDate}
                    onChange={(e) => setEventDate(e.target.value)}
                  />
                </label>
              </div>
              <div class="form-group my-4 text-start">
                <label htmlFor="event">Add event description</label>
                <textarea
                  class="form-control"
                  id="exampleFormControlTextarea1"
                  rows="3"
                  onChange={(e) => setEvent(e.target.value)}
                ></textarea>
                <small style={{ color: "red", fontSize: "0.8rem" }}>
                  {errMsg}
                </small>
              </div>
            </form>
          </div>
          <div className="modal-footer">
            <button
              type="button"
              className="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Close
            </button>
            <button
              type="button"
              className="btn btn-primary"
              onClick={() => handle()}
            >
              Submit
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EditEventModal;
