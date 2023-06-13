import React, { useState, useRef, useEffect } from "react";
import { registerUser } from "../../service/UserService";

const USERNAME_REGEX = /^[a-zA-Z][a-zA-Z0-9-_]{5,20}$/;
const EMAIL_REGEX = /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/;
const PWD_REGEX =
  /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

const RegistrationForm = ({ handleSignUp }) => {
  const userRef = useRef(null);

  const [username, setUsername] = useState("");
  const [validName, setValidName] = useState(false);
  const [nameErr, setNameErr] = useState("");

  const [email, setEmail] = useState("");
  const [validEmail, setValidEmail] = useState(false);
  const [emailErr, setEmailErr] = useState("");

  const [pwd, setPwd] = useState("");
  const [validPwd, setValidPwd] = useState(false);
  const [pwdErr, setPwdErr] = useState("");

  const [matchPwd, setmatchPwd] = useState("");
  const [validMatch, setValidMatch] = useState(false);
  const [matchPwdErr, setmatchPwdErr] = useState("");

  const [errMsg, setErrMsg] = useState("");

  useEffect(() => {
    userRef.current.focus();
  }, []);

  useEffect(() => {
    setValidName(USERNAME_REGEX.test(username));
    setNameErr(
      !USERNAME_REGEX.test(username) && username.length > 0
        ? "Invalid username. Username should be alphanumeric with 5-20 characters."
        : ""
    );
  }, [username]);

  useEffect(() => {
    setValidEmail(EMAIL_REGEX.test(email));
    setEmailErr(
      !EMAIL_REGEX.test(email) && email.length > 0
        ? "Invalid email address."
        : ""
    );
  }, [email]);

  useEffect(() => {
    setValidPwd(PWD_REGEX.test(pwd));
    setPwdErr(
      !PWD_REGEX.test(pwd) && pwd.length > 0
        ? "Invalid password. Password should contain at least one lowercase letter, one uppercase letter, one digit, one special character, and be at least 8 characters long."
        : ""
    );
    console.log(matchPwd === pwd ? true : false) 
    setValidMatch(matchPwd === pwd);
    setmatchPwdErr(
      ((matchPwd === pwd ? false : true) && matchPwd.length > 0)
        ? "Invalid password.Confirm should match the password."
        : ""
    );
  }, [pwd, matchPwd]);

  useEffect(() => {
    setErrMsg("");
  }, [username, email, pwd, matchPwd]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (validEmail && validName && validPwd && validMatch) {
      registerUser(username, email, pwd)
        .then((data) => {
          resetAll();
          handleSignUp(data)
        })
        .catch((error) => {
          console.log(error);
          setErrMsg(error.message);
        });
    } else {
      setErrMsg("Invalid inputs.");
    }
  };

  const resetAll = () => {
    setUsername("");
    setEmail("");
    setPwd("");
    setmatchPwd("");
  }

  return (
    <form onSubmit={handleSubmit}>
      <div class="form-group my-4">
        <label htmlFor="username">Username</label>
        <input
          type="text"
          class="form-control mt-2"
          placeholder="Please enter a username"
          id="username"
          ref={userRef}
          onChange={(e) => setUsername(e.target.value)}
          value={username}
          required
        />
        {!errMsg && nameErr && (
          <p style={{ color: "red", fontSize: "0.8rem" }}>{nameErr}</p>
        )}
        <p style={{ color: "red", fontSize: "0.8rem" }}>{errMsg}</p>
      </div>
      <div class="form-group my-4">
        <label htmlFor="email">Email</label>
        <input
          type="text"
          class="form-control mt-2"
          placeholder="Please enter an email"
          id="email"
          onChange={(e) => setEmail(e.target.value)}
          required
          value={email}
        />
        {!errMsg && emailErr && (
          <p style={{ color: "red", fontSize: "0.8rem" }}>{emailErr}</p>
        )}
        <p style={{ color: "red", fontSize: "0.8rem" }}>{errMsg}</p>
      </div>
      <div class="form-group my-4">
        <label htmlFor="password">Password</label>
        <input
          type="password"
          class="form-control mt-2"
          placeholder="Please enter your password"
          id="password"
          required
          onChange={(e) => setPwd(e.target.value)}
          value={pwd}
        />
        {!errMsg && pwdErr && <p style={{ color: "red", fontSize: "0.8rem" }}>{pwdErr}</p>}
        <p style={{ color: "red", fontSize: "0.8rem" }}>{errMsg}</p>
      </div>
      <div class="form-group my-4">
        <label htmlFor="confirm_password">Confirm Password</label>
        <input
          type="password"
          class="form-control mt-2"
          placeholder="Please enter your password again"
          id="confirm_password"
          required
          onChange={(e) => setmatchPwd(e.target.value)}
          value={matchPwd}
        />
        {!errMsg && matchPwdErr && (
          <p style={{ color: "red", fontSize: "0.8rem" }}>{matchPwdErr}</p>
        )}
        <p style={{ color: "red", fontSize: "0.8rem" }}>{errMsg}</p>
      </div>
      <div className="form-group text-center">
        <input type="submit" value="Sign up" class="button button-primary " />
      </div>
    </form>
  );
};

export default RegistrationForm;
