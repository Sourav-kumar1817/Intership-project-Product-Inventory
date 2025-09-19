// src/services/api.js
import axios from "axios";

const API_URL = "http://localhost:8080/products";

export const fetchProducts = async (params = {}) => {
  // Convert params object to query string
  const query = new URLSearchParams(params).toString();
  const response = await axios.get(`${API_URL}?${query}`);
  return response.data;
};

export const createProduct = async (product) => {
  const response = await axios.post(API_URL, product);
  return response.data;
};

export const deleteProduct = async (id) => {
  await axios.delete(`${API_URL}/${id}`);
};
