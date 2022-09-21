import React, { useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

function Login() {
  const [Email, SetEmail] = useState("");
  const [Password, SetPassword] = useState("");
  const [Info, SetInfo] = useState("");

  const emailHandler = (e) => {
    e.preventDefault();
    SetEmail(e.target.value);
  };

  const passwordHandler = (e) => {
    e.preventDefault();
    SetPassword(e.target.value);
  };

  const submitHandler = (e) => {
    e.preventDefault();
    // state에 저장한 값을 가져옵니다.
    console.log(Email);
    console.log(Password);

    let body = {
      email: Email,
      password: Password,
    };

    axios.post("/api/sign-in", body).then((res) => SetInfo(res));
    console.log(Info);
  };

  return (
    <>
      <div>
        <Row><Col></Col></Row>
        <Row>s</Row>
        <Row>s</Row>
        <Row>s</Row>
        <Form onSubmit={submitHandler}>
          <Container>
            <Row>
              <Col lg={4}></Col>
              <Col lg={4}>
                <Form.Group
                  className="mb-3"
                  value={Email}
                  onChange={emailHandler}
                  controlId="formBasicEmail"
                >
                  <Form.Control type="email" placeholder="Email" />
                </Form.Group>
              </Col>
            </Row>
          </Container>
          <Container>
            <Row>
              <Col lg={4}></Col>
              <Col lg={4}>
                <Form.Group
                  className="mb-3"
                  value={Password}
                  onChange={passwordHandler}
                  controlId="formBasicPassword"
                >
                  <Form.Control type="password" placeholder="Password" />
                </Form.Group>
              </Col>
            </Row>
          </Container>
          <Container>
            <Row>
              <Col lg={4}></Col>
              <Col lg={4}>
                <div className="d-grid gap-2">
                  <Form.Group className="mx-auto">
                    <Button type="submit" variant="primary">
                      로그인
                    </Button>
                    {""}
                  </Form.Group>
                </div>
              </Col>
            </Row>
          </Container>
          <Container>
            <Row>
              <Col lg={4}></Col>
              <Col lg={4}>
                <div className="d-grid gap-2">
                  <Form.Group className="mx-auto">
                    <Link to="/signup">
                      <Button variant="light">회원가입</Button>
                    </Link>
                  </Form.Group>
                </div>
              </Col>
            </Row>
          </Container>
        </Form>
      </div>
    </>
  );
}

export default Login;
