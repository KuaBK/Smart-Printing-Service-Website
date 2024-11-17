import React, {createContext, useState, useContext, useEffect} from 'react';
import toast from 'react-hot-toast';
import api from '../Services/api.jsx'

const ContextApi = createContext();

export const ContextProvider = ({children}) => {
    // Tìm token in the localstronge
    const getToken = localStrorange.getItem("JWT_TOKEN") ? JSON.stringify(localStrorange.getItem("JWT_TOKEN")) : "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0cmxvYzEyIiwiZW1haWwiOiJ0cmxvY25lQGdtYWlsLmNvbSIsInJvbGVzIjoiU1RVREVOVCIsImlhdCI6MTczMTc3MDI3MiwiZXhwIjoxNzMxOTQzMDcyfQ.1AKZy5_90FIcNnpX_yvXQ4V3ElCdpRLpugI2GY9aYIk";

    // Find is the user status from localStorage
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
    // const [selecInput, setSelecInput] = useState('HomePageUser')
    // const [noti, setNoti] = useState([
    //     {
    //     time: "10:00 AM - 22/11/2022",
    //     avatar: "https://example.com/avatar1.png",
    //     title: "Notification 1"
    //     },
    //     {
    //     time: "11:00 AM - 21/12/2021",
    //     avatar: "https://example.com/avatar2.png",
    //     title: "Notification 2"
    //     },
    //     {
    //     time: "12:00 PM - 20/10/2020",
    //     avatar: "https://example.com/avatar3.png",
    //     title: "Notification 3"
    //     }
    // ]);
    // const [bill, setBill] = useState([
    //     {
    //     time: "10:00 AM - 22/11/2022",
    //     title: "#12093"
    //     },
    //     {
    //     time: "11:00 AM - 21/12/2021",
    //     title: "#01934"
    //     },
    //     {
    //     time: "12:00 PM - 20/10/2020",
    //     title: "#245532"
    //     }
    // ]);
    useEffect(() => {
        fetchUser();
    }, [token]);

    

    return (
        <ContextApi.Provider 
            value={{
                token, 
                setToken, 
                currentUser, 
                setCurrentUser, 
                isAdmin, 
                setIsAdmin
            }}
        >
            {children}
        </ContextApi.Provider>
    )
    
}

export const useAuth = () => {
    return useContext(ContextApi);
}