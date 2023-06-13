import React, { useState, useEffect } from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";
import "./Loginsignup.css";
import LoginForm from "../../components/forms/LoginForm";

const Login = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [previousUrl, setPreviousUrl] = useState("");

  useEffect(() => {
    setPreviousUrl(
      location?.state?.previousUrl ? location.state.previousUrl : "/"
    );
  }, []);

  const handleLogin = (data) => {
    localStorage.setItem("user", JSON.stringify(data));
    navigate(previousUrl);
  };

  return (
    <section className="p-5 mb-5">
      <div class="d-lg-flex half shadow-lg">
        <div class="bg order-1 order-md-2"></div>
        <div class="contents order-2 order-md-1">
          <div class="container">
            <div class="row align-items-center justify-content-center">
              <div class="col-md-7">
                <h3>
                  Login to <strong>locality</strong>
                </h3>
                <LoginForm handleLogin={handleLogin}/>
                <hr />
                <h6 className="text-center">
                  Become a member ,{" "}
                  <Link to="/signup" state={{ previousPath: previousUrl }}>
                    Sign up
                  </Link>
                </h6>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default Login;
