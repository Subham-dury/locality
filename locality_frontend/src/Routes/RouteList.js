import React, { useEffect, useState } from "react";
import { Route, Routes, Navigate, useLocation } from "react-router-dom";
import Home from "../Pages/Home/Home";
import Login from "../Pages/Login/Login";
import Signup from "../Pages/Login/Signup";
import Report from "../Pages/Report/Report";
import Review from "../Pages/Review/Review";
import UserReport from "../Pages/User/UserReports/UserReport";
import UserReview from "../Pages/User/UserReviews/UserReview";
import Protected from "./Protected";

const RouteList = () => {
  const [isSignedIn, setIsSignedIn] = useState(false);
  const location = useLocation();

  useEffect(() => {
    
    setIsSignedIn(localStorage.getItem("isLoggedIn") ? true : false);
  }, [location]);

  return (
    <Routes>
      <Route path="*" element={<Navigate to="/" />} />
      <Route path="/" >
        <Route index element={<Home />} />
        <Route path="reviews" element={<Review />} />
        <Route path="events" element={<Report />} />
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
