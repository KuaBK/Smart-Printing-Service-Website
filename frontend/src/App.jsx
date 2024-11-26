import './App.css'
import { BrowserRouter as Router, Route, Routes , Navigate} from "react-router-dom";
import { HomePage } from './components/HomePage';
import { Signup } from './components/Signup';
import Admin from './Pages/Admin';
import Spso from './Pages/Spso';
import User from './Pages/User';
import ProtectedRoute from './components/ProtectedRoute';
import { useContext } from 'react';


import { BuyPrintingPages } from '../src/components/BuyPrintingPages'
import { Payment } from '../src/components/Payment'
import { ConfigPrint } from '../src/components/ConfigPrint'
import { ConfigSPSO } from '../src/components/ConfigSPSO'
// import { HomePage } from '../src/components/HomePage'
import { HomePageAdmin } from '../src/components/HomePageAdmin'
import { HomePageUser } from '../src/components/HomePageUser'
import { HomePageSPSO } from '../src/components/HomePageSPSO'
import { Library } from '../src/components/Library'
import { ManagePrint } from '../src/components/ManagePrint'
import { Navbar } from '../src/components/Navbar'
import { PrintService } from '../src/components/PrintService'
import { Slidebar } from '../src/components/Sidebar'
import { StatisticsSPSO } from '../src/components/StatisticsSPSO'
import { Profile } from '../src/components/Profile'
import { Notifications } from '../src/components/Notifications'
import {HistoryTransaction} from '../src/components/HistoryTransaction'
import Profile1 from './Pages/Profile1'
import History from './Pages/History'

function getContent() {
  const role = localStorage.getItem("ROLE")
  console.log(role)
  if (!role) {
    return <HomePage />
  }
  if (role === "admin") {
    return <Navigate to="/admin" />
  }
  if (role === "user") {
    return <Navigate to="/user" />
  }
  if (role === "spso") {
    return <Navigate to="/spso" />
  }
  return <HomePage />
}

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={getContent()} />
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
            <ProtectedRoute name="SPSO">
              <Spso />
            </ProtectedRoute>
          }
        >
          <Route index element={<Navigate to="/spso/hompage" replace />} />
          <Route path="hompage" element={<HomePageSPSO />} />
          <Route path="manageprint" element={<ConfigPrint />} />
          <Route path="statistics" element={<StatisticsSPSO />} />
          <Route path="config" element={<ConfigSPSO />} />
        </Route>
        <Route
          path="/user"
          element={
            <ProtectedRoute name="STUDENT" children = {<User />} />
          }
        >
          <Route index element={<Navigate to="/user/hompage" replace />} />
          <Route path="hompage" element={<HomePageUser />} />
          <Route path="lib" element={<Library />} />
          <Route path="buypages" element={<BuyPrintingPages />} />
          <Route path="config" element={<PrintService />} />
        </Route>
        <Route path="/profile" element={<Profile1 />} />
        <Route path="/history" element={<History />} />
        <Route path="*" element={<HomePage />} />
      </Routes>
    </Router>
  )
}

export default App
