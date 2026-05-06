import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:8080",
});

export const setAuthToken = (token) => {
  API.defaults.headers.common["Authorization"] = `Bearer ${token}`;
};

export default API;