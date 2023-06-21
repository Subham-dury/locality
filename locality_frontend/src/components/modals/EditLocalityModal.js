import React, {useState, useContext, useEffect} from 'react'
import DataContext from "../../context/DataContext";
import { updateLocalityItem } from '../../service/LocalityService';

const EditLocalityModal = ({lid, handleCloseModal}) => {
    const {loadLocalityList} = useContext(DataContext);

    const [name, setName] =useState(null);
    const [city, setCity] = useState(null);
    const [state, setState] = useState(null);
    const [about, setAbout] = useState(null);
    const [errMsg, setErrMsg] = useState(null);

    useEffect(() =>{
        setErrMsg(null)
    }, [name, city, state, about])

   
    const handle = () => {
        console.log({name, city, state, about})
        console.log(lid)
        updateLocalityItem({name, city, state, about}, lid)
        .then((data) => {
            console.log(data);
            reset();
            closeModal();
            handleCloseModal();
            loadLocalityList();
          })
          .catch((error) => {
            console.log(error);
            setErrMsg(error.message);
          });

    }

    function reset() {
        setName(null);
        setCity(null);
        setAbout(null);
        setState(null);
    }



    function closeModal() {
        document.getElementById("staticBackdrop").classList.remove("show");
        document
          .querySelectorAll(".modal-backdrop")
          .forEach((el) => el.classList.remove("modal-backdrop"));
      }

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
              Modify the locality
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
              <div className="form-group">
                <label>Name</label>
                <input
                  type="text"
                  className="i-box form-control input-md my-2"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                />
              </div>
              <div className="form-group">
                <label>City</label>
                <input
                  type="text"
                  className="i-box form-control input-md my-2"
                  value={city}
                  onChange={(e) => setCity(e.target.value)}
                />
              </div>
              <div className="form-group">
                <label>Description</label>
                <input
                  type="text"
                  className="i-box form-control input-md my-2"
                  value={state}
                  onChange={(e) => setState(e.target.value)}
                />
              </div>
              <div className="form-group">
                <label>About</label>
                <input
                  type="text"
                  className="i-box form-control input-md my-2"
                  value={about}
                  onChange={(e) => setAbout(e.target.value)}
                />
              </div>
              <small style={{ color: "red", fontSize: "0.8rem" }}>
                {errMsg}
              </small>
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

export default EditLocalityModal;
