import React, { useState, useEffect } from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";
import "./loginsignup.css";

const Login = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [previousUrl, setPreviousUrl] = useState(''); 

  useEffect(() => {
    setPreviousUrl(location?.state?.previousUrl ? location.state.previousUrl : '/')
  })

  const handleLogin = (event) => {
    event.preventDefault();
    localStorage.setItem("isLoggedIn", "true");
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

                <form onSubmit={handleLogin}>
                  <div class="form-group my-4">
                    <label htmlFor="username">Email</label>
                    <input
                      type="text"
                      class="form-control mt-2"
                      placeholder="your-email@gmail.com"
                      id="username"
                    />
                  </div>
                  <div class="form-group my-4">
                    <label htmlFor="password">Password</label>
                    <input
                      type="password"
                      class="form-control mt-2"
                      placeholder="Your Password"
                      id="password"
                    />
                  </div>
                  <div className="form-group">
                    <input
                      type="submit"
                      value="Log In"
                      class="button button-primary"
                    />
                  </div>
                </form>
                <hr />

                <h6 className="text-center">
                  Become a member ,{" "}
                  <Link
                    to="/signup"
                    state={{ previousPath: previousUrl}}
                  >
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
