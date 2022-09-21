import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import Home from "./view/home";
import About from "./view/about";
import Profile from "./view/profile";
import Login from "./view/login";
import Axios from "./view/axios";
import Signup from "./view/signup";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

function App() {
  return (
    <BrowserRouter>
      <header>
        <Navbar bg="light" expand="lg">
          <Container>
            <Navbar.Brand href="/">Study Matching</Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
              <Nav className="me-auto">
                <Nav.Link href="/about">About</Nav.Link>
                <Nav.Link href="/profile">매칭하기</Nav.Link>
                <Nav.Link href="/profile">워크스페이스</Nav.Link>
                <Nav.Link
                  href="/login"
                  style={{
                    position: "absolute",
                    right: 80,
                    marginRight: "30px",
                  }}
                >
                  Login
                </Nav.Link>
                <Nav.Link
                  href="/signup"
                  style={{
                    position: "absolute",
                    right: 0,
                    marginRight: "30px",
                  }}
                >
                  회원가입
                </Nav.Link>
              </Nav>
            </Navbar.Collapse>
          </Container>
        </Navbar>
      </header>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
      </Routes>
      <footer>footer</footer>
    </BrowserRouter>
  );
}
export default App;
