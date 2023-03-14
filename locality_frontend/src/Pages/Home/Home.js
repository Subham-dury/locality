import React from "react";
import AboutUs from "./AboutUs";
import Hero from "./Hero";
import RecentReviews from "./RecentReviews";
import RecentEvents from "./RecentEvents";
import Newsletter from "./Newsletter";
import './Home.css';

const Home = () => {
  return (
    <>
      <Hero />
      <AboutUs />
      <RecentReviews/>
      <RecentEvents/>
      <Newsletter />
    </>
  );
};

export default Home;
