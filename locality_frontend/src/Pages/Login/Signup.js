import React, { useEffect } from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";

const Signup = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const handleSignUp = (event) => {
    event.preventDefault();
    localStorage.setItem("isLoggedIn", "true");
    navigate(location.state.previousPath);
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
                  Signup to <strong>locality</strong>
                </h3>

                <form onSubmit={handleSignUp}>
                  <div class="form-group my-4">
                    <label htmlFor="username">Username</label>
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
                  <div className="form-group text-center">
                    <input
                      type="submit"
                      value="Sign up"
                      class="button button-primary "
                    />
                  </div>
                </form>
                <hr />
                <h6 className="text-center">
                  Already a member ,{" "}
                  <Link
                    to="/login"
                    state={{ previousUrl: location.state.previousPath }}
                  >
                    Log in
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

export default Signup;
