import React, { useState, useEffect } from "react";
import { Container, Typography, Box, Button, Stack, Paper } from "@mui/material";
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
  }, []); // fixed infinite loop issue by passing empty dependency array

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
    <Container maxWidth="lg" sx={{ paddingY: 4 }}>
      <Typography variant="h4" gutterBottom align="center" sx={{ fontWeight: 'bold', color: '#1976d2' }}>
        Product Management
      </Typography>

      <Paper sx={{ padding: 3, marginBottom: 3, boxShadow: 3 }}>
        <ProductForm addProduct={handleAddProduct} />
      </Paper>

      <Paper sx={{ padding: 3, marginBottom: 3, boxShadow: 3 }}>
        <AdvancedSearchBar onSearch={handleSearch} />
      </Paper>

      <Paper sx={{ padding: 3, marginBottom: 3, boxShadow: 3 }}>
        <ProductTable products={products} onDelete={handleDelete} />
      </Paper>

      <Stack direction="row" spacing={2} justifyContent="center" alignItems="center" marginTop={2}>
        <Button variant="contained" color="primary" onClick={handlePrevPage} disabled={page === 0}>
          Previous
        </Button>
        <Typography variant="subtitle1">Page {page + 1}</Typography>
        <Button variant="contained" color="primary" onClick={handleNextPage} disabled={products.length < size}>
          Next
        </Button>
      </Stack>
    </Container>
  );
}

export default App;
