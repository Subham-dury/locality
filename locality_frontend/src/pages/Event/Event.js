import React, { useState, useEffect,useContext } from "react";
import SelectLocality from "../../components/selectors/LocalitySelector";
import Localityinfo from "../../components/cards/LocalityInfoCard";
import LocalityNotFound from "../../components/cards/LocalityNotFoundCard";
import DataNotFoundCard from "../../components/cards/DataNotFoundCard";
import EventFilterbar from "./EventFilterbar";
import EventContainer from "./EventContainer";
import { localitylist } from "../../Data/LocalityList";
import DataContext from "../../context/DataContext";
import {
  getAllEvents,
  getEventByLocality,
  getEventByEventType,
  getEventByLocalityAndEventType,
} from "../../service/EventService";
import "./Event.css";
import { eventTypes } from "../../Data/EventTypeList";

const Event = () => {

  const {eventTypelist} = useContext(DataContext)
  const [localityitem, setLocalityitem] = useState(null);
  const [typeOfEvent, setTypeOfEvent] = useState(null);
  const [isLocalityListLoaded, setIsLocalityListLoaded] = useState(false);

  const [events, setEvents] = useState([]);
  const [errorInEvents, setErrorInEvents] = useState(null);

  const refresh = () => {
    setEventsToAll()
  }

  useEffect(() => {
    setEventsToAll();
  }, []);

  const setLocality = (option) => {
    
    if (option != 0) {
      setLocalityitem(
        localitylist.filter((locality) => locality.localityId == option)[0]
      );
      setIsLocalityListLoaded(true);

      if (typeOfEvent!=null && typeOfEvent.eventTypeId != 0) { 
        setEventsByLocalityAndType(option, typeOfEvent.eventTypeId);
      } else {
        setEventsByLocality(option);
      }
    } else {
      setIsLocalityListLoaded(false);
      if (typeOfEvent != null) {
        setEventsByType(typeOfEvent.eventTypeId);
      } else {
        setEventsToAll();
      }
    }
  };

  const setEventType = (option) => {
    if (option != 0) {
      setTypeOfEvent(
        eventTypelist.filter((type) => type.eventTypeId == option)[0]
      );
      if (localityitem!=null && localityitem.localityId != 0) {
        setEventsByLocalityAndType(localityitem.localityId, option);
      } else {
        setEventsByType(option);
      }
    } else {
      if (localityitem!=null && localityitem.localityId != 0) {
        setEventsByLocality(localityitem.localityId);
      } else {
        setEventsToAll();
      }
    }
  };

  const setEventsToAll = () => {
    getAllEvents()
      .then((data) => {
        setEvents(data);
        setErrorInEvents(null);
      })
      .catch((error) => {
        setErrorInEvents(error.message);
        setEvents([]);
      });
  };

  const setEventsByType = (option) => {
    getEventByEventType(option)
    .then((data) => {
      setEvents(data)
      setErrorInEvents(null)
    })
    .catch((error) => {
      setEvents([])
      setErrorInEvents(error.message)
    })
  };

  const setEventsByLocality = (option) => {
    getEventByLocality(option)
      .then((data) => {
        setEvents(data);
        setErrorInEvents(null);
      })
      .catch((error) => {
        setEvents([]);
        setErrorInEvents(error.message);
      });
  };

  const setEventsByLocalityAndType = (localityId, eventTypeId) => {

    getEventByLocalityAndEventType(localityId, eventTypeId)
      .then((data) => {
        setEvents(data);
        setErrorInEvents(null);
      })
      .catch((error) => {
        setEvents([]);
        setErrorInEvents(error.message);
      });
  };

  return (
    <section className="event">
      <div className="container">
        <div className="row my-4 justify-content-center">
          <SelectLocality
            localitylist={localitylist}
            setLocality={setLocality}
          />
        </div>
        {isLocalityListLoaded && (
          <div className="row localitydesc my-4">
            <Localityinfo localityitem={localityitem} />
          </div>
        )}
        {!isLocalityListLoaded && <LocalityNotFound />}
        <div className="row my-3 mx-1">
          <EventFilterbar
            name={
              isLocalityListLoaded
                ? `events for ${localityitem.name}`
                : `All events`
            }
            setEventType={setEventType}
            eventTypes={eventTypelist}
            refresh={refresh}
          />
        </div>
        <div className="row my-3">
          {!errorInEvents && <EventContainer events={events} />}
          {errorInEvents && <DataNotFoundCard message={errorInEvents} />}
        </div>
      </div>
    </section>
  );
};

export default Event;
