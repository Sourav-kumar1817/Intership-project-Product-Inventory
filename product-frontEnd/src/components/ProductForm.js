import React, { useState, useEffect } from "react";
import {
  TextField,
  Button,
  Grid,
  Typography,
  Paper,
  IconButton,
  Stack,
} from "@mui/material";
import { AddCircle, RemoveCircle } from "@mui/icons-material";

function ProductForm({ initialData = null, onSubmit }) {
  const emptyCharacteristic = { name: "", valueType: "String", value: "" };
  const emptyRelatedParty = { name: "", role: "" };

  const [formData, setFormData] = useState({
    name: "",
    description: "",
    status: "ACTIVE",
    productSerialNumber: "",
    characteristics: [emptyCharacteristic],
    relatedParties: [emptyRelatedParty],
  });

  // Pre-fill form for Edit safely
  useEffect(() => {
    if (initialData) {
      const safeCharacteristics = Array.isArray(initialData.characteristics)
        ? initialData.characteristics.map((c) => ({
            name: c.name || "",
            value: c.value || "",
            valueType: c.valueType || "String",
          }))
        : [emptyCharacteristic];

      const safeRelatedParties = Array.isArray(initialData.relatedParties)
        ? initialData.relatedParties.map((r) => ({
            name: r.name || "",
            role: r.role || "",
          }))
        : [emptyRelatedParty];

      setFormData({
        name: initialData.name || "",
        description: initialData.description || "",
        status: initialData.status || "ACTIVE",
        productSerialNumber: initialData.productSerialNumber || "",
        characteristics: safeCharacteristics,
        relatedParties: safeRelatedParties,
      });
    }
  }, [initialData]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleArrayChange = (field, index, subField, value) => {
    const updatedArray = [...formData[field]];
    updatedArray[index][subField] = value;
    setFormData((prev) => ({ ...prev, [field]: updatedArray }));
  };

  const handleAddField = (field, newObj) => {
    setFormData((prev) => ({ ...prev, [field]: [...prev[field], newObj] }));
  };

  const handleRemoveField = (field, index) => {
    const updatedArray = [...formData[field]];
    updatedArray.splice(index, 1);
    setFormData((prev) => ({ ...prev, [field]: updatedArray }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (onSubmit) onSubmit(formData);

    // Reset only in Add mode
    if (!initialData) {
      setFormData({
        name: "",
        description: "",
        status: "ACTIVE",
        productSerialNumber: "",
        characteristics: [emptyCharacteristic],
        relatedParties: [emptyRelatedParty],
      });
    }
  };

  return (
    <Paper sx={{ padding: 4, mb: 3, boxShadow: 3, backgroundColor: "#EDE9FE" }}>
      <Typography variant="h6" gutterBottom sx={{ fontWeight: "bold", color: "#1976d2" }}>
        {initialData ? "Edit Product" : "Add New Product"}
      </Typography>

      <form onSubmit={handleSubmit}>
        <Grid container spacing={2}>
          {/* Product Name */}
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label="Product Name"
              name="name"
              value={formData.name}
              onChange={handleChange}
              required
            />
          </Grid>

          {/* Serial Number */}
          <Grid item xs={12} sm={6}>
            <TextField
              fullWidth
              label="Serial Number"
              name="productSerialNumber"
              value={formData.productSerialNumber}
              onChange={handleChange}
              required
            />
          </Grid>

          {/* Description */}
          <Grid item xs={12}>
            <TextField
              fullWidth
              multiline
              rows={3}
              label="Description"
              name="description"
              value={formData.description}
              onChange={handleChange}
            />
          </Grid>

          {/* Characteristics */}
          <Grid item xs={12}>
            <Typography variant="subtitle1" sx={{ fontWeight: "medium" }}>Characteristics</Typography>
            {formData.characteristics.map((c, index) => (
              <Grid container spacing={1} key={index} alignItems="center" sx={{ mb: 1 }}>
                <Grid item xs={4}>
                  <TextField
                    fullWidth
                    label="Name"
                    value={c.name}
                    onChange={(e) => handleArrayChange("characteristics", index, "name", e.target.value)}
                  />
                </Grid>
                <Grid item xs={4}>
                  <TextField
                    fullWidth
                    label="Value"
                    value={c.value}
                    onChange={(e) => handleArrayChange("characteristics", index, "value", e.target.value)}
                  />
                </Grid>
                <Grid item xs={3}>
                  <TextField
                    fullWidth
                    label="Value Type"
                    value={c.valueType}
                    onChange={(e) => handleArrayChange("characteristics", index, "valueType", e.target.value)}
                  />
                </Grid>
                <Grid item xs={1}>
                  <IconButton onClick={() => handleRemoveField("characteristics", index)} disabled={formData.characteristics.length === 1}>
                    <RemoveCircle color="error" />
                  </IconButton>
                </Grid>
              </Grid>
            ))}
            <Button variant="outlined" startIcon={<AddCircle />} sx={{ mt: 1, mb: 2 }} onClick={() => handleAddField("characteristics", emptyCharacteristic)}>
              Add Characteristic
            </Button>
          </Grid>

          {/* Related Parties */}
          <Grid item xs={12}>
            <Typography variant="subtitle1" sx={{ fontWeight: "medium" }}>Related Parties</Typography>
            {formData.relatedParties.map((r, index) => (
              <Grid container spacing={1} key={index} alignItems="center" sx={{ mb: 1 }}>
                <Grid item xs={5}>
                  <TextField
                    fullWidth
                    label="Name"
                    value={r.name}
                    onChange={(e) => handleArrayChange("relatedParties", index, "name", e.target.value)}
                  />
                </Grid>
                <Grid item xs={5}>
                  <TextField
                    fullWidth
                    label="Role"
                    value={r.role}
                    onChange={(e) => handleArrayChange("relatedParties", index, "role", e.target.value)}
                  />
                </Grid>
                <Grid item xs={2}>
                  <IconButton onClick={() => handleRemoveField("relatedParties", index)} disabled={formData.relatedParties.length === 1}>
                    <RemoveCircle color="error" />
                  </IconButton>
                </Grid>
              </Grid>
            ))}
            <Button variant="outlined" startIcon={<AddCircle />} sx={{ mt: 1, mb: 2 }} onClick={() => handleAddField("relatedParties", emptyRelatedParty)}>
              Add Related Party
            </Button>
          </Grid>

          {/* Submit */}
          <Grid item xs={12}>
            <Stack direction="row" justifyContent="center">
              <Button type="submit" variant="contained" color="primary" sx={{ px: 6 }}>
                {initialData ? "Update Product" : "Save Product"}
              </Button>
            </Stack>
          </Grid>
        </Grid>
      </form>
    </Paper>
  );
}

export default ProductForm;
