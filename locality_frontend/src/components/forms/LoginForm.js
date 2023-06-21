import React, { useState, useEffect, useRef } from "react";
import { loginUser } from "../../service/UserService";

const USERNAME_REGEX = /^[a-zA-Z][a-zA-Z0-9-_]{5,20}$/;
const EMAIL_REGEX = /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/;
const PWD_REGEX =
  /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

const LoginForm = ({ handleLogin }) => {
  const userRef = useRef();

  const [isEmail, setIsEmail] = useState(false);
  const [user, setUser] = useState("");
  const [userErr, setUserErr] = useState("");


  const [pwd, setpwd] = useState("");
  const [pwdErr, setPwdErr] = useState("");

  const [errMsg, setErrMsg] = useState("");

  useEffect(() => {
    userRef.current.focus();
  }, []);

  useEffect(() => {
    setErrMsg("");
    setPwdErr("");
    setUserErr("");
  }, [user, pwd]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(pwdErr === "" &&  userErr==="")
 
    if(!validate){
      loginUser(user, pwd, isEmail)
      .then((data) => {
        console.log("after api call")
        setUser("");
        setpwd("");
        handleLogin(data);
      })
      .catch((error) => {
        console.log(error);
        setErrMsg(error.message);
      });
    }
    else{
      if(errMsg==="" && userErr === ""){
        setErrMsg("invalid inputs")
      }
    }
  };

  const validate = () => {
    if(!EMAIL_REGEX.test(user) && !USERNAME_REGEX.test(user)){
      console.log("al failed")
      setUserErr("Inavlid username or email");
      return false;
    }
    else if(!EMAIL_REGEX.test(user) && USERNAME_REGEX.test(user)){
      console.log("username pass")
      setUserErr("")
      setIsEmail(false)
    }
    else {
      console.log("email pass")
      setUserErr("")
      setIsEmail(true)
    }
    if(!PWD_REGEX.test(pwd)){
      setPwdErr("Invalid password")
      return false;
    }else{
      setPwdErr("");
    }
    return true;
  };

  return (
    <form onSubmit={handleSubmit}>
      <div class="form-group my-4">
        <label htmlFor="username">Email/Username</label>
        <input
          type="text"
          class="form-control mt-2"
          placeholder="Enter your email or password"
          id="username"
          ref={userRef}
          onChange={(e) => setUser(e.target.value)}
          value={user}
          required
        />
        {!errMsg && userErr && (
          <p style={{ color: "red", fontSize: "0.8rem" }}>{userErr}</p>
        )}
        <p style={{ color: "red", fontSize: "0.8rem" }}>{errMsg}</p>
      </div>
      <div class="form-group my-4">
        <label htmlFor="password">Password</label>
        <input
          type="password"
          class="form-control mt-2"
          placeholder="Enter your Password"
          id="password"
          onChange={(e) => setpwd(e.target.value)}
          value={pwd}
          required
        />
        {!errMsg && pwdErr && (
          <p style={{ color: "red", fontSize: "0.8rem" }}>{pwdErr}</p>
        )}
        <p style={{ color: "red", fontSize: "0.8rem" }}>{errMsg}</p>
      </div>
      <div className="form-group">
        <input type="submit" value="Log In" class="button button-primary" />
      </div>
    </form>
  );
};

export default LoginForm;
