import React, { useEffect, useState } from "react";
import { Route, Routes, Navigate, useLocation } from "react-router-dom";
import Event from "../pages/Event/Event";
import Home from "../pages/Home/Home";
import Login from "../pages/Login/Login";
import Signup from "../pages/Login/Signup";
import Review from "../pages/Review/Review";
import UserEvent from "../pages/User/UserEvents/UserEvent";
import UserReview from "../pages/User/UserReviews/UserReview";
import Protected from "./Protected";
import Locality from "../pages/Admin/Locality/Locality";
import EventType from "../pages/Admin/EventType/EventType";

const RouteList = () => {
  const [isSignedIn, setIsSignedIn] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);
  const location = useLocation();

  useEffect(() => {
    setIsSignedIn(localStorage.getItem("token") ? true : false);
    setIsAdmin(localStorage.getItem("user") && JSON.parse(localStorage.getItem("user")).role == "ADMIN" ? true : false);
    console.log(JSON.parse(localStorage.getItem("user")).role == "ADMIN")
  }, [location]);

  return (
    <Routes>
      <Route path="*" element={<Navigate to="/" />} />
      <Route path="/">
        <Route index element={<Home />} />
        <Route path="reviews" element={<Review />} />
        <Route path="events" element={<Event />} />
        <Route
          path="user-reviews"
          element={
            <Protected isSignedIn={isSignedIn}>
              <UserReview />
            </Protected>
          }
        />
        <Route
          path="user-reports"
          element={
            <Protected isSignedIn={isSignedIn}>
              <UserEvent />
            </Protected>
          }
        />
        <Route
          path="admin-locality"
          element={
            <Protected isSignedIn={isSignedIn}>
              {isAdmin && <Locality />}
            </Protected>
          }
        ></Route>
        <Route
          path="admin-eventtype"
          element={
            <Protected isSignedIn={isSignedIn}>
              {isAdmin && <EventType />}
            </Protected>
          }
        ></Route>
        <Route
          path="login"
          element={
            <Protected isSignedIn={!isSignedIn}>
              <Login />
            </Protected>
          }
        />
        <Route
          path="signup"
          element={
            <Protected isSignedIn={!isSignedIn}>
              <Signup />
            </Protected>
          }
        />
      </Route>
    </Routes>
  );
};

export default RouteList;
