import React, { useEffect, useState } from "react";
import AboutUs from "./AboutUs";
import Hero from "./Hero";
import Newsletter from "./Newsletter";
import RecentData from "./RecentData";
import DataNotFoundCard from "../../components/Cards/DataNotFoundCard";
import {
  getRecentReview,
  getRecentEvent,
} from "../../service/RecentReviewEventsService";
import "./Home.css";

const Home = () => {
  const [recentReview, setRecentReview] = useState([]);
  const [recentEvent, setRecentEvent] = useState([]);
  const [errorInReview, setErrorInReview] = useState(null);
  const [errorInEvent, setErrorInEvent] = useState(null);

  useEffect(() => {
    getRecentReview()
      .then((data) => setRecentReview(data))
      .catch((error) => setErrorInReview(error));

      getRecentEvent()
      .then((data) => setRecentEvent(data))
      .catch((error) => setErrorInEvent(error));
  }, []);

  return (
    <>
      <Hero />
      <AboutUs />

      {!recentReview && !recentEvent && <div>Loading...</div>}


     {errorInReview && <DataNotFoundCard message={"Failed to load recent reviews"}/>}

      {!errorInReview && recentReview && (
        <RecentData
          data={recentReview}
          isReview={true}
          title={"Recent Reviews"}
          buttonVal={"All reviews"}
          redirect={"/reviews"}
          dataTarget={"review"}
        />
      )}
      {errorInEvent && <DataNotFoundCard message={"Failed to load recent events"}/>}      
      {!errorInEvent && recentEvent && (
        <RecentData
          data={recentEvent}
          isReview={false}
          title={"Recent Events"}
          buttonVal={"All events"}
          redirect={"/events"}
          dataTarget={"event"}
        />
      )}
      <Newsletter />
    </>
  );
};

export default Home;
