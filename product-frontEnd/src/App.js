import React, { useState, useEffect } from "react";
import { Container, Typography, Button, Stack, Paper } from "@mui/material";
import ProductForm from "./components/ProductForm";
import ProductTable from "./components/ProductTable";
import AdvancedSearchBar from "./components/AdvancedSearchBar";
import { fetchProducts, createProduct, deleteProduct, updateProduct } from "./services/api";

function App() {
  const [view, setView] = useState("search"); // "search", "add", "edit", "editForm"
  const [products, setProducts] = useState([]);
  const [page, setPage] = useState(0);
  const [size] = useState(4);
  const [searchParams, setSearchParams] = useState({});
  const [editProductData, setEditProductData] = useState(null);

  // Transform product to safe structure
  const transformProduct = (p) => ({
    ...p,
    characteristics: Array.isArray(p.characteristics)
      ? p.characteristics.map((c) => ({
          name: c.name || "-",
          value: c.value || "-",
          valueType: c.valueType || "String"
        }))
      : [],
    relatedParties: Array.isArray(p.relatedParties)
      ? p.relatedParties.map((r) => ({
          name: r.name || "-",
          role: r.role || "-"
        }))
      : []
  });

  const loadProducts = async (params = {}, pageNumber = 0) => {
    try {
      const queryParams = { ...params, page: pageNumber, size };
      const data = await fetchProducts(queryParams);

      // Transform all products
      const mapped = data.map(transformProduct);

      setProducts(mapped);
      setPage(pageNumber);
    } catch {
      alert("Failed to fetch products.");
    }
  };

  useEffect(() => {
    loadProducts({}, 0);
  }, []);

  const handleAddProduct = async (productData) => {
    try {
      await createProduct(productData);
      loadProducts(searchParams, 0);
      setView("search");
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

  const handleEdit = (product) => {
    setEditProductData(product);
    setView("editForm");
  };

  const handleUpdateProduct = async (updatedData) => {
    try {
      await updateProduct(editProductData.id, updatedData);
      setEditProductData(null);
      setView("edit");
      loadProducts(searchParams, page);
    } catch {
      alert("Failed to update product.");
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
      <Typography
        variant="h4"
        gutterBottom
        align="center"
        sx={{ fontWeight: "bold", color: "#1976d2" }}
      >
        Product Management
      </Typography>

      <Stack direction="row" spacing={2} sx={{ marginBottom: 3 }}>
        <Button
          variant={view === "search" ? "contained" : "outlined"}
          onClick={() => setView("search")}
        >
          Search Product
        </Button>
        <Button
          variant={view === "add" ? "contained" : "outlined"}
          onClick={() => setView("add")}
        >
          Add Product
        </Button>
        <Button
          variant={view === "edit" ? "contained" : "outlined"}
          onClick={() => setView("edit")}
        >
          Edit Product
        </Button>
      </Stack>

      {view === "search" && (
        <>
          <Paper sx={{ padding: 3, marginBottom: 3, boxShadow: 3 }}>
            <AdvancedSearchBar onSearch={handleSearch} />
          </Paper>

          <Paper sx={{ padding: 3, marginBottom: 3, boxShadow: 3 }}>
            <ProductTable products={products} onDelete={handleDelete} mode="search" />
          </Paper>

          <Stack direction="row" spacing={2} justifyContent="center" marginTop={2}>
            <Button variant="contained" color="primary" onClick={handlePrevPage} disabled={page === 0}>
              Previous
            </Button>
            <Typography variant="subtitle1">Page {page + 1}</Typography>
            <Button variant="contained" color="primary" onClick={handleNextPage} disabled={products.length < size}>
              Next
            </Button>
          </Stack>
        </>
      )}

      {view === "add" && (
        <Paper sx={{ padding: 3, marginBottom: 3, boxShadow: 3 }}>
          <ProductForm onSubmit={handleAddProduct} />
        </Paper>
      )}

      {view === "edit" && (
        <Paper sx={{ padding: 3, marginBottom: 3, boxShadow: 3 }}>
          <Typography variant="h6" gutterBottom>Edit Product (Search)</Typography>
          <AdvancedSearchBar onSearch={handleSearch} />
          <ProductTable products={products} onDelete={handleDelete} onEdit={handleEdit} mode="edit" />
        </Paper>
      )}

      {view === "editForm" && editProductData && (
        <Paper sx={{ padding: 3, marginBottom: 3, boxShadow: 3 }}>
          <ProductForm initialData={editProductData} onSubmit={handleUpdateProduct} />
        </Paper>
      )}
    </Container>
  );
}

export default App;
