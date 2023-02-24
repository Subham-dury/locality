import React from 'react'
import { useNavigate } from 'react-router-dom'

const Login = () => {

  const navigate = useNavigate();

  const handleLogin = (event) => {
    event.preventDefault();
    sessionStorage.setItem("isLoggedIn", "true")
    navigate(-1);
  }

  return (
    <div>
      <h1>Login</h1>
      <button className="btn btn-primary" onClick={handleLogin}>Login</button>
    </div>
  )
}

export default Login