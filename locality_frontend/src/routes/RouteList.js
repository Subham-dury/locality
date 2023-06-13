import React, { useEffect, useState } from "react";
import { Route, Routes, Navigate, useLocation } from "react-router-dom";
import Event from "../pages/Event/Event";
import Home from "../pages/Home/Home";
import Login from "../pages/Login/Login";
import Signup from "../pages/Login/Signup";
import Review from "../pages/Review/Review";
import UserReport from "../pages/User/UserReports/UserReport";
import UserReview from "../pages/User/UserReviews/UserReview";
import Protected from "./Protected";

const RouteList = () => {
  const [isSignedIn, setIsSignedIn] = useState(false);
  const location = useLocation();

  useEffect(() => {
    setIsSignedIn(localStorage.getItem("token") ? true : false);
  }, [location]);

  return (
    <Routes>
      <Route path="*" element={<Navigate to="/" />} />
      <Route path="/" >
        <Route index element={<Home />} />
        <Route path="reviews" element={<Review />} />
        <Route path="events" element={<Event/>} />
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
              <UserReport />
            </Protected>
          }
        />
        <Route
          path="login"
          element={
            <Protected isSignedIn={!isSignedIn}>
              <Login/>
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
