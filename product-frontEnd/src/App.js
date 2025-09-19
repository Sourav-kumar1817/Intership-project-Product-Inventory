import React, { useState, useEffect } from "react";
import ProductForm from "./components/ProductForm";
import ProductTable from "./components/ProductTable";
import AdvancedSearchBar from "./components/AdvancedSearchBar";
import { fetchProducts, createProduct, deleteProduct } from "./services/api";

function App() {
  const [products, setProducts] = useState([]);
  const [page, setPage] = useState(0);      
  const [size] = useState(2);      
  const [searchParams, setSearchParams] = useState({}); 

  const loadProducts = async (params = {}, pageNumber = 0) => {
    try {
      const queryParams = { ...params, page: pageNumber, size };
      const data = await fetchProducts(queryParams);

      const mapped = data.map((p) => ({
        ...p,
        characteristics: p.characteristics
          ?.map((c) => `${c.name}: ${c.value} (${c.valueType})`)
          .join(", "),
        relatedParty: p.relatedParties
          ?.map((r) => `${r.name} (${r.role})`)
          .join(", ")
      }));

      setProducts(mapped);
      setPage(pageNumber); 
    } catch (error) {
      alert("Failed to fetch products.");
    }
  };

  useEffect(() => {
    loadProducts();
  });

  const handleAddProduct = async (productData) => {
    try {
      await createProduct(productData);
      loadProducts(searchParams, 0); 
    } catch {
      alert("Failed to create product.");
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteProduct(id);
      loadProducts(searchParams, page); 
    } catch {
      alert("Failed to delete product.");
    }
  };

  const handleSearch = async (params) => {
    setSearchParams(params); 
    loadProducts(params, 0); 
  };

  const handlePrevPage = () => {
    if (page > 0) loadProducts(searchParams, page - 1);
  };

  const handleNextPage = () => {
    if (products.length === size) loadProducts(searchParams, page + 1);
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Product Management</h2>
      <ProductForm addProduct={handleAddProduct} />
      <AdvancedSearchBar onSearch={handleSearch} />
      <ProductTable products={products} onDelete={handleDelete} />

      <div style={{ marginTop: "20px", display: "flex", gap: "10px", alignItems: "center" }}>
        <button onClick={handlePrevPage} disabled={page === 0}>
          Previous
        </button>
        <span>Page {page + 1}</span>
        <button onClick={handleNextPage} disabled={products.length < size}>
          Next
        </button>
      </div>
    </div>
  );
}

export default App;
