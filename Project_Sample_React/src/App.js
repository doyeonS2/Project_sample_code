import './App.css';
import Login from './Login';
import Signup from './signup/Signup';
import GoHome from './home/Home';
import MemberReg from './pages/MemberReg';
import MemberInfo from './pages/MemberInfo';
import MemberDetail from './pages/MemberDetail';
import TestCk from './pages/TestCk';
import { useState } from 'react';
import { BrowserRouter as Router, Route, Routes} from "react-router-dom";
import BottomMenu from './pages/BottomMenu';
import SocketTest from './pages/SocketTest';

function App() {
  const [isName, setIsName] = useState("0000");
  return (
    <>
    <Router>
      <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/Signup" element={<Signup />} />
          <Route path="/Home" element={<GoHome />} />
          <Route path="/MemberInfo" element={<MemberInfo />} />
          <Route path="/MemberReg" element={<MemberReg />} />
          <Route path="/MemberDetail" element={<MemberDetail />} />
          <Route path="/TestCk" element={<TestCk />} />
          <Route path="/TestCk" element={<SocketTest />} />
      </Routes>
      <BottomMenu/>
    </Router>
      
    </>
  );
}

export default App;
