import React from "react";

const UserEventsContainer = ({ events, deleteAEvent, refresh }) => {
  const [show, setShow] = useState(false);
  const [rid, setRid] = useState("");
  const [errMsg, setErrMsg] = useState("");
  const [event, setEvent] = useState("");

  const handleDeleteReview = (eventId) => {
    deleteAEvent(eventId);
  };

  useEffect(() => {
    setErrMsg("");
  }, [event]);

  const handle = () => {
    setErrMsg(
      !(event.length > 9 && event.length < 256)
        ? "Review must have 10 to 255 characters"
        : ""
    );

    if (event.length > 9 && event.length < 256) {
      update();
    }
  };

  const update = () => {
    updateReview(rid, review)
      .then((data) => {
        closeModal();
        handleCloseModal();
      })
      .catch((error) => {
        console.log(error);
        setErrMsg(error.message);
      });
  };

  useEffect(() => {
    handleOpenModal();
  }, [show]);

  const handleOpenModal = () => {
    setShow(true);
  };

  const handleCloseModal = () => {
    setShow(false);
    refresh();
  };

  function closeModal() {
    document.getElementById("staticBackdrop").classList.remove("show");
    document
      .querySelectorAll(".modal-backdrop")
      .forEach((el) => el.classList.remove("modal-backdrop"));
  }
  return (
    <div class="user-events-container">
      <div class="row row-cols-xxl-2">
        {reviews.map((review) => {
          return (
            <div key={review.reviewId} className="my-4">
              <div className="details-container">
                <ReviewCard item={review} />
                <div
                  className="btn-group"
                  role="group"
                  aria-label="Basic example"
                >
                  <button
                    type="button"
                    className="button button-dark my-2"
                    onClick={() => {
                      handleOpenModal();
                      setRid(review.reviewId);
                    }}
                    data-bs-toggle="modal"
                    data-bs-target="#staticBackdrop"
                  >
                    Edit
                  </button>
                  <button
                    type="button"
                    className="button button-primary my-2"
                    onClick={() => handleDeleteReview(review.reviewId)}
                  >
                    Delete
                  </button>
                </div>
              </div>
            </div>
          );
        })}
      </div>
      {show && (
        <EditReviewModal
          handle={handle}
          errMsg={errMsg}
          setReview={setReview}
        />
      )}
    </div>
  );
};

export default UserEventsContainer;
