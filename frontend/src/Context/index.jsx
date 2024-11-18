import React, {createContext, useState, useContext, useEffect} from "react"
import toast from 'react-hot-toast';
import api from '../Services/api.jsx'

export const GlobalContext = createContext(null)

export default function GlobalState({ children }) {
  const [selecInput, setSelecInput] = useState('HomePageUser')
  const [fileChoice, setFileChoice] = useState();
  const [profile, setProfile] = useState();
  const [noti, setNoti] = useState([
    {
      time: "10:00 AM - 22/11/2022",
      avatar: "https://example.com/avatar1.png",
      title: "Notification 1"
    },
    {
      time: "11:00 AM - 21/12/2021",
      avatar: "https://example.com/avatar2.png",
      title: "Notification 2"
    },
    {
      time: "12:00 PM - 20/10/2020",
      avatar: "https://example.com/avatar3.png",
      title: "Notification 3"
    }
  ]);
  const [bill, setBill] = useState([
    {
      time: "10:00 AM - 22/11/2022",
      title: "#12093"
    },
    {
      time: "11:00 AM - 21/12/2021",
      title: "#01934"
    },
    {
      time: "12:00 PM - 20/10/2020",
      title: "#245532"
    }
  ]);
  const getToken = localStorage.getItem("JWT_TOKEN") 
    ? localStorage.getItem("JWT_TOKEN") 
    : null;
    // : "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0cmxvYzEyIiwiZW1haWwiOiJ0cmxvY25lQGdtYWlsLmNvbSIsInJvbGVzIjoiU1RVREVOVCIsImlhdCI6MTczMTc3MDI3MiwiZXhwIjoxNzMxOTQzMDcyfQ.1AKZy5_90FIcNnpX_yvXQ4V3ElCdpRLpugI2GY9aYIk";
  const [token, setToken] = useState(getToken);

  const [currentUser, setCurrentUser] = useState(null);

  const fetchUser = async () => {
      const user = JSON.parse(localStorage.getItem('USER'));
      console.log("USER:", user);
      if (user?.username){
          try {
              const {data} = await api.get(`/auth/user`);
              setCurrentUser(data);
          }
          catch (error){
              console.error("Failed to fetch user", error);
              toast.error("Failed to fetch current user");
          }
      }
  }
  useEffect(() => {
      fetchUser();
  }, [token]);
  return (
    <GlobalContext.Provider
      value={{
        selecInput,
        setSelecInput,
        noti, 
        setNoti,
        bill, 
        setBill,
        token, 
        setToken,
        currentUser,
        setCurrentUser,
        fileChoice, 
        setFileChoice,
        profile, 
        setProfile
      }}
    >
      {children}
    </GlobalContext.Provider>
  )
}