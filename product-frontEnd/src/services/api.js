import axios from "axios";

const API_URL = "http://localhost:8080/products";

// Fetch products with optional query parameters
export const fetchProducts = async (params = {}) => {
  const query = new URLSearchParams(params).toString();
  const response = await axios.get(`${API_URL}?${query}`);
  return response.data;
};
// Create a new product
export const createProduct = async (product) => {
  const response = await axios.post(API_URL, product);
  return response.data;
};

// Delete a product by ID
export const deleteProduct = async (id) => {
  await axios.delete(`${API_URL}/${id}`);
};

// Update a product by ID (partial or full)
export const updateProduct = async (id, updates) => {
  const response = await axios.patch(`${API_URL}/${id}`, updates);
  return response.data;
};
