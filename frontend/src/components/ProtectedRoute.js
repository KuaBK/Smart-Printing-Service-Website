import React, { useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { GlobalContext } from '../Context';

const ProtectedRoute = ({children}) => {
    const { token } = useContext(GlobalContext);

    const navigate = useNavigate();

    useEffect(() => {
        if (!token) {
            navigate("/login", { replace: true });
        }
    }, [token, navigate]);

    if (!token) {
        return null;
    }

    return children;
};

export default ProtectedRoute;