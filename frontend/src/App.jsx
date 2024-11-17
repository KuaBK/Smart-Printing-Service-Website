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
            <ProtectedRoute >
              <Admin />
            </ProtectedRoute>
          }
        />
        <Route
          path="/spso"
          element={
            <ProtectedRoute>
              <Spso />
            </ProtectedRoute>
          }
        />
        <Route
          path="/user"
          element={
            <ProtectedRoute>
              <User />
            </ProtectedRoute>
          }
        />
        <Route path="*" element={<HomePage />} />
      </Routes>
    </Router>
    // <div>
    //   <HomePage />
    // </div>
  )
}

export default App
