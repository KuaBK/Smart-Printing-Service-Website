import './App.css'
import { BrowserRouter as Router, Route, Routes , Navigate} from "react-router-dom";
import { HomePage } from './components/HomePage';
import { Signup } from './components/Signup';
import Admin from './Pages/Admin';
import Spso from './Pages/Spso';
import User from './Pages/User';
import ProtectedRoute from './components/ProtectedRoute';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<Signup />} />
        <Route
          path="/admin"
          element={
            <ProtectedRoute name="ADMIN" children = {<Admin />} />
          }
        />
        <Route
          path="/spso"
          element={
            <ProtectedRoute name="SPSO" children = {<Spso />} />
          }
        />
        <Route
          path="/user"
          element={
            <ProtectedRoute name="STUDENT" children = {<User />} />
          }
        />
        <Route path="*" element={<HomePage />} />
      </Routes>
    </Router>
  )
}

export default App
