import React, {useState, useContext, useEffect} from 'react'
import DataContext from '../../context/DataContext';
import { saveLocalityItem } from '../../service/LocalityService';

const LocalityForm = () => {

    const {loadLocalityList} = useContext(DataContext);

    const [name, setName] =useState(null);
    const [city, setCity] = useState(null);
    const [state, setState] = useState(null);
    const [about, setAbout] = useState(null);
    const [errMsg, setErrMsg] = useState(null);

    useEffect(() =>{
        setErrMsg(null)
    }, [name, city, state, about])

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log({name, city, state, about})
        saveLocalityItem({name, city, state, about})
        .then((data) => {
            console.log(data);
            reset();
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


  return (
    <div className="row d-flex justify-content-center">
          <div className="col-sm-8 col-sm-offset-2">
            <form onSubmit={handleSubmit} className="pad-bg">
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
                <small style={{ color: "red", fontSize: "0.8rem" }}>{errMsg}</small>
              <div className="form-group">
                <button className="button button-primary" type='submit'>Add</button>
              </div>
            </form>
          </div>
        </div>
  )
}

export default LocalityForm