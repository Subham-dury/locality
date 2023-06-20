import React, { useState, useEffect, useContext } from "react";
import DataContext from "../../../context/DataContext";
import LocalityContainer from "./LocalityContainer";
import LocalityForm from "../../../components/forms/LocalityForm";

const Locality = () => {
  const { localitylist}= useContext(DataContext);

  return (
    <section className="locality-section">
      <div className="container mt-3">
      <h2>Add locality</h2>
        <LocalityForm/>
      </div>
      <div className="container mt-3">
      <h2>Manage locality</h2>
        <LocalityContainer localitylist={localitylist} />
      </div>
    </section>
  );
};

export default Locality;
