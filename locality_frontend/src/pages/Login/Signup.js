import React, { useEffect, useState } from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";
import RegistrationForm from "../../components/forms/RegistrationForm";

const Signup = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [previousPath, SetPreviousPath] = useState('')

  useEffect(() => {
    SetPreviousPath(location?.state?.previousPath ? location.state.previousPath : '/')
  },[])

  const handleSignUp = (data) => {
    localStorage.setItem("user", JSON.stringify(data));
    navigate(previousPath);
  };

  return (
    <section className="p-5 my-5">
      <div class="d-lg-flex half shadow-lg">
        <div class="bg order-2 order-md-1"></div>
        <div class="contents order-1 order-md-2">
          <div class="container">
            <div class="row align-items-center justify-content-center">
              <div class="col-md-7 mt-3">
                <h3>
                  Signup to <strong>locality</strong>
                </h3>

                <RegistrationForm handleSignUp={handleSignUp}/>
                <hr />
                <h6 className="text-center">
                  Already a member ,{" "}
                  <Link
                    to="/login"
                    state={{ previousUrl: previousPath }}
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
