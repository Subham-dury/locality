import React, {useEffect, useState} from "react";
import AboutUs from "./AboutUs";
import Hero from "./Hero";
import Newsletter from "./Newsletter";
import { events } from "../../Data/RecentEvents";
import { reviews } from "../../Data/RecentReviews";

import "./Home.css";
import RecentData from "./RecentData";

const Home = () => {

  const[recentReview, setRecentReview] = useState([]);
  const[recentEvent, setRecentEvent] = useState(null);


  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch("http://localhost:8080/review/recent");
      const data = await response.json();
      console.log(data)

    };
    fetchData();
  }, []);


  return (
    <>
      <Hero />
      <AboutUs />
      <RecentData
        data={reviews}
        title={"Recent Reviews"}
        buttonVal={"All reviews"}
        redirect={"/reviews"}
        dataTarget={"review"}
      />
      <RecentData
        data={events}
        title={"Recent Events"}
        buttonVal={"All events"}
        redirect={"/events"}
        dataTarget={"event"}
      />
      <Newsletter />
    </>
  );
};

export default Home;
