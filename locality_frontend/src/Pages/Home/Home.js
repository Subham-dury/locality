import React from "react";
import AboutUs from "./AboutUs";
import FeaturedReports from "./FeaturedReports";
import FeaturedReviews from "./FeaturedReviews";
import Hero from "./Hero";
import './Home.css';
import Newsletter from "./Newsletter";

const Home = () => {
  return (
    <>
      <Hero />
      <AboutUs />
      <FeaturedReviews />
      <FeaturedReports />
      <Newsletter />
    </>
  );
};

export default Home;
