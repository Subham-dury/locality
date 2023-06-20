import React,{ createContext, useState, useEffect } from 'react'
import { getLocalityList } from '../service/LocalityService';
import { getEventTypeList } from '../service/EventTypeService';

const DataContext = createContext();

export const DataProvider = ({children}) => {
    const [localitylist, setLocalitylist] = useState([]);
    const [eventTypelist, setEventTypelist] = useState([]);
    const [error, setError] = useState(null)

    useEffect(() => {
        loadLocalityList();
        loadEventTypeList();
    }, [])

    function loadLocalityList() {
      getLocalityList()
      .then((data) => {
        setLocalitylist(data);
        setError(null)
      })
      .catch((error) => {
        setLocalitylist([]);
        setError(error.message)
      });
    }

    function loadEventTypeList() {
      getEventTypeList()
      .then((data) => {
        setEventTypelist(data);
        setError(null)
      })
      .catch((error) => {
        setEventTypelist([]);
        setError(error.message)
      });
    }

  return (
    <DataContext.Provider value={{localitylist, loadLocalityList, eventTypelist, loadEventTypeList}}>
        {children}
    </DataContext.Provider>
  )
}

export default DataContext;