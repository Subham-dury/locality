import React, { useContext,useState, useEffect } from "react";
import DataContext from "../../../context/DataContext";
import EditEventTypeModal from "../../../components/modals/EditEventTypeModal";
import { deleteEventType } from "../../../service/EventTypeService";
import EventTypeCard from "../../../components/cards/EventTypeCard";

const EventTypeContainer = ({eventTypelist}) => {

    const {loadEventTypeList} = useContext(DataContext);
    const [show, setShow] = useState(false);
    const [tid, setTid] = useState("");
    
  useEffect(() => {
    handleOpenModal();
  }, [show]);

  const handleDeleteEventType = (id) => {
    console.log(id);
    deleteEventType(id)
      .then((data) => {
        console.log(data);
        loadEventTypeList();
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handleOpenModal = () => {
    setShow(true);
  };

  const handleCloseModal = () => {
    setShow(false);
    loadEventTypeList();
  };
  return (
    <>
      <div class="user-reviews-container">
        <div class="row row-cols-2 d-flex justify-content-center">
          {eventTypelist.map((item) => {
            return (
              <div key={item.eventTypeId} class>
                <div className="">
                  <EventTypeCard typeItem={item} />
                  <div
                    className="btn-group"
                    role="group"
                    aria-label="Basic example"
                  >
                    <button
                      type="button"
                      className="btn btn-primary my-2 mx-2"
                      onClick={() => {
                        handleOpenModal();
                        setTid(item.eventTypeId);
                      }}
                      data-bs-toggle="modal"
                      data-bs-target="#staticBackdrop"
                    >
                      Edit
                    </button>
                    <button
                      type="button"
                      className="btn btn-secondary my-2 mx-2"
                      onClick={() => handleDeleteEventType(item.eventTypeId)}
                    >
                      Delete
                    </button>
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      </div>
      {
        show && <EditEventTypeModal tid={tid} handleCloseModal={handleCloseModal}/>
      }
    </>
  );
}

export default EventTypeContainer