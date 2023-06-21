import React, {useState, useContext, useEffect} from 'react'
import DataContext from "../../context/DataContext";
import { updateEventTypeItem } from '../../service/EventTypeService';

const EditEventTypeModal = ({tid, handleCloseModal}) => {

    const {loadEventTypeList} = useContext(DataContext);

    const [type, setType] =useState(null);
    const [errMsg, setErrMsg] = useState(null);

    useEffect(() =>{
        setErrMsg(null)
    }, [type])

    const handle = () => {
      console.log({type})
      console.log(tid)
      updateEventTypeItem(type, tid)
      .then((data) => {
          console.log(data);
          setType(null);setErrMsg(null);
          closeModal();
          handleCloseModal();
          loadEventTypeList();
        })
        .catch((error) => {
          console.log(error);
          setErrMsg(error.message);
        });

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
              Modify the event type
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
                  value={type}
                  onChange={(e) => setType(e.target.value)}
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
  )
}

export default EditEventTypeModal