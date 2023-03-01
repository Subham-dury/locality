import React from "react";
import AboutUs from "./AboutUs";
import FeaturedEvents from "./FeaturedEvents";

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
      <FeaturedEvents/>
      <Newsletter />
    </>
  );
};

export default Home;
