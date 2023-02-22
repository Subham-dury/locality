import { useEffect, useState } from "react";
import {  Link , useNavigate, useLocation} from "react-router-dom";
import logo from "../assests/logo.png";

const Navbar = () => {

  const navigate = useNavigate()
  const location = useLocation()

  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(()=>{
    //console.log(location)
    setIsLoggedIn(sessionStorage.getItem("isLoggedIn") ? true : false)
  }, [location])

  const handleLogin = () => {
    navigate("/login")
  }

  const handleLogout = () => {
    sessionStorage.clear();
    navigate(location.pathname)
  }

  return (
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <div class="container">
        <a class="navbar-brand" href="#">
          <img src={logo} alt="locality" />
        </a>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNavDropdown"
          aria-controls="navbarNavDropdown"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
          <ul class="navbar-nav ms-auto text-center">
            <li class="nav-item mx-3">
              <Link class="nav-link" aria-current="page" to="/">
                Home
              </Link>
            </li>
            <li class="nav-item mx-3">
              <Link class="nav-link" to="/reviews">
                Reviews
              </Link>
            </li>
            <li class="nav-item mx-3">
              <Link class="nav-link" to="/reports">
                Reports
              </Link>
            </li>
            {isLoggedIn && (
              <li class="nav-item mx-3">
                <Link class="nav-link" to="/user-reviews">
                  Your Reviews
                </Link>
              </li>
            )}
            {isLoggedIn && (
              <li class="nav-item mx-3">
                <Link class="nav-link" to="/user-reports">
                  Your Reports
                </Link>
              </li>
            )}
          </ul>
          <div class="d-grid ms-auto gap-2 col-md-2">
            {!isLoggedIn && (
              <button class="btn btn-primary me-md-2" type="button" onClick={handleLogin}>
                login
              </button>
            )}
            {isLoggedIn && (
              <button class="btn btn-primary me-md-2" type="button" onClick={handleLogout}>
                logout
              </button>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
