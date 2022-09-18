import React, { useState } from 'react';
import axios from "axios";

function Signup() {
  const [Email, SetEmail] = useState("");
  const [Password, SetPassword] = useState("");
  const [Nickname, SetNickname] = useState("");
  const [Username, SetUsername] = useState("");


  const emailHandler = (e) => {
    e.preventDefault();
    SetEmail(e.target.value);
  };

  const passwordHandler = (e) => {
    e.preventDefault();
    SetPassword(e.target.value);
  };

  const nicknameHandler = (e) => {
    e.preventDefault();
    SetNickname(e.target.value);
  };

  const usernameHandler = (e) => {
    e.preventDefault();
    SetUsername(e.target.value);
  };
  const submitHandler = (e) => {
    e.preventDefault();
    // state에 저장한 값을 가져옵니다.
    console.log(Email);
    console.log(Password);
    console.log(Nickname);
    console.log(Username);

    let body = {
      email: Email,
      password: Password,
      username: Username,
      nickname: Nickname
    };

    axios
      .post("/api/sign-up", body)
      .then((res) => console.log(res));
  };

  return (
    <>
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          width: "100%",
          height: "100vh",
        }}
      >
        <form
          onSubmit={submitHandler}
          style={{ display: "flex", flexDirection: "Column" }}
        >
          <label>Email</label>
          <input type="email" value={Email} onChange={emailHandler}></input>
          <label>Password</label>
          <input
            type="password"
            value={Password}
            onChange={passwordHandler}
          ></input>
          <label>Nickname</label>
          <input type="nickname" value={Nickname} onChange={nicknameHandler}></input>
          <label>Username</label>
          <input type="username" value={Username} onChange={usernameHandler}></input>
          <button type="submit">회원가입</button>
        </form>
      </div>
    </>
  );
};

export default Signup;