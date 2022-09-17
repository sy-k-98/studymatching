import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import Home from './view/home';
import About from './view/about';
import Profile from './view/profile';
import Axios from './view/axios';

function App() {
  return (
  <BrowserRouter>
  <nav>
    <Link to='/'>Home</Link>
    <br />
    <Link to='/about'>About</Link>
    <br />
    <Link to='/profile'>Profile</Link>
  </nav>
    <header>----------------------------------</header>
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/about" element={<About />} />
      <Route path="/profile" element={<Profile />} />
    </Routes>
    <footer>----------------------------------</footer>
  </BrowserRouter>
  );
}
export default App;