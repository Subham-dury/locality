import { useEffect, useState } from "react";
import { Link, NavLink, useNavigate, useLocation } from "react-router-dom";
import logo from "../assests/logo.png";

const Navbar = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    //console.log(location)
    setIsLoggedIn(sessionStorage.getItem("isLoggedIn") ? true : false);
  }, [location]);

  const handleLogin = () => {
    navigate("/login", {
      state : {
        previousUrl: location.pathname,
      }
    });
  };

  const handleLogout = () => {
    sessionStorage.clear();
    navigate(location.pathname);
  };

  return (
    <nav className={`navbar navbar-expand-md bg-light`}>
      <div className="container">
        <Link className="navbar-brand" to="/">
          <img src={logo} alt="locality" />
        </Link>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNavDropdown"
          aria-controls="navbarNavDropdown"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNavDropdown">
          <ul className="navbar-nav ms-auto mb-2 mb-lg-0 text-center">
            <li className="nav-item mx-lg-3">
              <NavLink
                className="nav-link"
                activeClassName="active"
                aria-current="page"
                to="/"
              >
                Home
              </NavLink>
            </li>
            <li className="nav-item mx-lg-3">
              <NavLink
                to="/reviews"
                className="nav-link"
                activeClassName="active"
              >
                Reviews
              </NavLink>
            </li>
            <li className="nav-item mx-lg-3">
              <NavLink
                className="nav-link"
                activeClassName="active"
                to="/events"
              >
                Events
              </NavLink>
            </li>

            {isLoggedIn && (
              <li className="nav-item dropdown mx-lg-3">
                <Link
                  className="nav-link"
                  href="#"
                  id="navbarDropdown"
                  role="button"
                  data-bs-toggle="dropdown"
                  aria-expanded="false"
                >
                  User
                </Link>
                <ul
                  className="dropdown-menu text-center shadow"
                  aria-labelledby="navbarDropdown"
                >
                  <li className="nav-item mx-3">
                    <Link className="nav-link" to="/user-reviews">
                      Reviews
                    </Link>
                  </li>
                  <li className="nav-item mx-3">
                    <Link className="nav-link" to="/user-reports">
                      Reports
                    </Link>
                  </li>
                </ul>
              </li>
            )}
          </ul>
          <div className="btn-container">
            {!isLoggedIn && (
              <button
                className="button button-primary"
                type="button"
                onClick={handleLogin}
              >
                login
              </button>
            )}
            {isLoggedIn && (
              <button
                className="button button-primary"
                type="button"
                onClick={handleLogout}
              >
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
