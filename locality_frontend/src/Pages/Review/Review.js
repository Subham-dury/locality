import React, { useState, useEffect } from "react";
import SelectLocality from "./SelectLocality";
import Localityinfo from "./Localityinfo";
import Filterbar from "./Filterbar";
import Reviewscontainer from "./Reviewscontainer";
import { localitylist } from "../../Data/LocalityList";
import { reviews } from "../../Data/RecentReviews";
import "./Review.css";

const Review = () => {
  const [localityitem, setLocalityitem] = useState({});

  useEffect(() => {
      setLocalityitem(localitylist.filter(locality => locality.id === 1)[0])
  }, [])

  const setOption = (option) => {
    setLocalityitem(localitylist.filter(locality => locality.id == option)[0])
  };

  return (
    <section className="reviev">
      <div className="container">
        <div className="row my-4 justify-content-center">
          <SelectLocality localitylist={localitylist} setlocality={setOption} />
        </div>
        <div className="row localitydesc my-4">
          <Localityinfo localityitem={localityitem}/>
        </div>
        <div className="row my-3 mx-1">
          <Filterbar name={localityitem.name}/>
        </div>
        <div className="row my-3">
          <Reviewscontainer reviews={reviews} />
        </div>
      </div>
    </section>
  );
};

export default Review;
