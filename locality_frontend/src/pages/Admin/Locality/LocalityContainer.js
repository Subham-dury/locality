import React, { useContext,useState, useEffect } from "react";
import LocalityCard from "../../../components/cards/LocalityCard";
import DataContext from "../../../context/DataContext";
import { deleteLocality } from "../../../service/LocalityService";
import EditLocalityModal from "../../../components/modals/EditLocalityModal";

const LocalityContainer = ({ localitylist }) => {
  const [show, setShow] = useState(false);
  const [lid, setLid] = useState("");
  const { loadLocalityList } = useContext(DataContext);

  const handleDeleteLocality = (id) => {
    console.log(id);
    deleteLocality(id)
      .then((data) => {
        console.log(data);
        loadLocalityList();
      })
      .catch((error) => {
        console.log(error);
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
    loadLocalityList();
  };

  return (
    <>
      <div class="user-reviews-container">
        <div class="row row-cols-xxl-2">
          {localitylist.map((item) => {
            return (
              <div key={item.locaityId} class>
                <div className="details-container">
                  <LocalityCard localityitem={item} />
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
                        setLid(item.localityId);
                      }}
                      data-bs-toggle="modal"
                      data-bs-target="#staticBackdrop"
                    >
                      Edit
                    </button>
                    <button
                      type="button"
                      className="button button-primary my-2"
                      onClick={() => handleDeleteLocality(item.localityId)}
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
        show && <EditLocalityModal lid={lid} handleCloseModal={handleCloseModal}/>
      }
    </>
  );
};

export default LocalityContainer;
