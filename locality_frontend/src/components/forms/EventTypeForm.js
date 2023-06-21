import React, {useState, useContext, useEffect} from 'react'
import DataContext from '../../context/DataContext';
import { saveEventTypeItem } from '../../service/EventTypeService';

const EventTypeForm = () => {    
    const {loadEventTypeList} = useContext(DataContext);

    const [eventType, setEventType] =useState(null);
    const [errMsg, setErrMsg] = useState(null);

    useEffect(() =>{
        setErrMsg(null)
    }, [eventType])

    const handleSubmit = (e) => {
        e.preventDefault();
        saveEventTypeItem(eventType)
        .then((data) => {
            console.log(data);
            reset();
            loadEventTypeList();
          })
          .catch((error) => {
            console.log(error);
            setErrMsg(error.message);
          });
    }

    function reset() {
      setEventType("")
      setErrMsg("")
    }

    return (
    <div className="row d-flex justify-content-center">
      <div className="col-sm-8 col-sm-offset-2 col-md-6">
        <form onSubmit={handleSubmit} className="pad-bg">
          <div className="form-group">
            <label>Event type</label>
            <input
              type="text"
              className="i-box form-control input-md my-2"
              value={eventType}
              onChange={(e) => setEventType(e.target.value)}
            />
          </div>
          <small style={{ color: "red", fontSize: "0.8rem" }}>{errMsg}</small>
          <div className="form-group">
            <button className="button button-primary" type="submit">
              Add
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default EventTypeForm;
