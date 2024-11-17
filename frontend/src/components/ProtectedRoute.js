import React, { useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { GlobalContext } from '../Context';

const ProtectedRoute = (props) => {
    const { token } = useContext(GlobalContext);

    const navigate = useNavigate();

    useEffect(() => {
        const user = JSON.parse(localStorage.getItem('USER'))
        if (!token || !user.roles.includes(props.name)) {
            navigate("/login", { replace: true });
        }
    }, [token, navigate]);

    if (!token) {
        return null;
    }

    return props.children;
};

export default ProtectedRoute;