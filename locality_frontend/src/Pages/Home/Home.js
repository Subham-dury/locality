import React from "react";
import AboutUs from "./AboutUs";
import FeaturedReports from "./FeaturedReports";
import FeaturedReviews from "./FeaturedReviews";
import Hero from "./Hero";

const Home = () => {
  return (
    <>
      <Hero/>
      <AboutUs/>
      <FeaturedReviews />
      {/* <FeaturedReports /> */}
    </>
  );
};

export default Home;
