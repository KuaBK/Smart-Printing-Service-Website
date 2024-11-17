import axios from 'axios';

const api = axios.create({
    baseURL: "http://localhost:8080/api",
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
    },
    withCredentials: true,
});

api.interceptors.request.use(
    async (config) => {
        const token = localStorage.getItem('JWT_TOKEN');
        if (token){
            config.headers.Authorization = `Bearer ${token}`;
        }
        let csrfToken = localStorage.getItem('CSRF_TOKEN');
        if (!csrfToken){
            try{
                console.log("CSRF_TOKEN" + csrfToken);
                const {data} = await axios.get(
                    `http://localhost:8080/api/csrf-token`,
                    {withCredentials: true}
                );
                csrfToken = data.csrfToken;
                localStorage.setItem('CSRF_TOKEN', csrfToken);
            }
            catch (error){
                console.error('Failed to fetch CSRF token', error);
            }
        }

        if (csrfToken){
            config.headers['X-CSRF-TOKEN'] = csrfToken;
        }
        
        console.log("X-XSRF-TOKEN" + csrfToken);
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
)

export default api;

