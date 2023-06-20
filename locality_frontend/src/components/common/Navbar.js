import { useEffect, useState } from "react";
import { Link, NavLink, useNavigate, useLocation } from "react-router-dom";
import logo from "../../assests/logo.png";

const Navbar = () => {
  const [isAdmin, setIsAdmin] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [username, setUsername] = useState('')

  useEffect(() => {
    if(localStorage.getItem("token")){
      setIsLoggedIn(true)
      setUsername(JSON.parse(localStorage.user).username)
    }
    else{
      setIsLoggedIn(false)
    }

    if(localStorage.getItem("user")){
      setIsAdmin(JSON.parse(localStorage.getItem("user")).role == "ADMIN" ? true : false);
    }
    else{
      setIsAdmin(false)
    }
  }, [location]);

  const handleLogin = () => {
    navigate("/login", {
      state: {
        previousUrl: location.pathname,
      },
    });
  };

  const handleLogout = () => {
    localStorage.clear();
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
          </ul>
          <div className="btn-container">
            {!isLoggedIn && (
              <button
                className="button button-primary loginbtn"
                type="button"
                onClick={handleLogin}
              >
                login
              </button>
            )}

            {isLoggedIn && (
              <li className="nav-item dropdown mx-lg-3">
                <button
                  className="button button-primary dropstart dropdown-toggle"
                  type="button"
                  data-bs-toggle="dropdown"
                  aria-expanded="false"
                >
                  {username}
                </button>
                <ul
                  className="dropdown-menu text-center shadow"
                  aria-labelledby="navbarDropdown"
                >
                  <li className="nav-item mx-3 my-3">
                    <Link className="nav-link" to="/user-reviews">
                      Manage Reviews
                    </Link>
                  </li>
                  <li className="nav-item mx-3 my-3">
                    <Link className="nav-link" to="/user-reports">
                      Manage Events
                    </Link>
                  </li>
                  {isAdmin && (
                    <li className="nav-item mx-3 my-3">
                      <Link className="nav-link" to="/admin-locality">
                        Manage Localities
                      </Link>
                    </li>
                  )}
                  {isAdmin && (
                    <li className="nav-item mx-3 my-3">
                      <Link className="nav-link" to="/admin-eventtype">
                        Manage Events
                      </Link>
                    </li>
                  )}
                  <button
                    className="button button-primary mx-2"
                    type="button"
                    onClick={handleLogout}
                  >
                    logout
                  </button>
                </ul>
              </li>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;

