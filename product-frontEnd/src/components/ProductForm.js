import React, { useState } from "react";
import {
  TextField,
  Button,
  Grid,
  Typography,
  Paper,
  IconButton
} from "@mui/material";
import { AddCircle, RemoveCircle } from "@mui/icons-material";

function ProductForm({ addProduct }) {
  const [formData, setFormData] = useState({
    name: "",
    description: "",
    status: "ACTIVE",
    productSerialNumber: "",
    characteristics: [{ name: "", valueType: "String", value: "" }],
    relatedParties: [{ name: "", role: "" }]
  });

  // Handle simple text field changes
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  // Handle dynamic array fields
  const handleArrayChange = (field, index, subField, value) => {
    const updatedArray = [...formData[field]];
    updatedArray[index][subField] = value;
    setFormData({ ...formData, [field]: updatedArray });
  };

  // Add new entry
  const handleAddField = (field, newObj) => {
    setFormData({ ...formData, [field]: [...formData[field], newObj] });
  };

  // Remove entry
  const handleRemoveField = (field, index) => {
    const updatedArray = [...formData[field]];
    updatedArray.splice(index, 1);
    setFormData({ ...formData, [field]: updatedArray });
  };

  // Submit form
  const handleSubmit = (e) => {
    e.preventDefault();
    addProduct(formData); // Calls backend via App.js
    setFormData({
      name: "",
      description: "",
      status: "ACTIVE",
      productSerialNumber: "",
      characteristics: [{ name: "", valueType: "String", value: "" }],
      relatedParties: [{ name: "", role: "" }]
    });
  };

  return (
    <Paper style={{ padding: "20px", marginBottom: "20px" }}>
      <Typography variant="h6" gutterBottom>
        Add New Product
      </Typography>
      <form onSubmit={handleSubmit}>
        <Grid container spacing={2}>
          {/* Name */}
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

          {/* Characteristics Section */}
          <Grid item xs={12}>
            <Typography variant="subtitle1">Characteristics</Typography>
            {formData.characteristics.map((c, index) => (
              <Grid
                container
                spacing={1}
                key={index}
                alignItems="center"
                style={{ marginBottom: "10px" }}
              >
                <Grid item xs={4}>
                  <TextField
                    fullWidth
                    label="Name"
                    value={c.name}
                    onChange={(e) =>
                      handleArrayChange("characteristics", index, "name", e.target.value)
                    }
                  />
                </Grid>
                <Grid item xs={4}>
                  <TextField
                    fullWidth
                    label="Value"
                    value={c.value}
                    onChange={(e) =>
                      handleArrayChange("characteristics", index, "value", e.target.value)
                    }
                  />
                </Grid>
                <Grid item xs={3}>
                  <TextField
                    fullWidth
                    label="Value Type"
                    value={c.valueType}
                    onChange={(e) =>
                      handleArrayChange("characteristics", index, "valueType", e.target.value)
                    }
                  />
                </Grid>
                <Grid item xs={1}>
                  <IconButton
                    onClick={() => handleRemoveField("characteristics", index)}
                    disabled={formData.characteristics.length === 1}
                  >
                    <RemoveCircle />
                  </IconButton>
                </Grid>
              </Grid>
            ))}
            <Button
              startIcon={<AddCircle />}
              onClick={() =>
                handleAddField("characteristics", {
                  name: "",
                  valueType: "String",
                  value: ""
                })
              }
            >
              Add Characteristic
            </Button>
          </Grid>

          {/* Related Parties Section */}
          <Grid item xs={12}>
            <Typography variant="subtitle1">Related Parties</Typography>
            {formData.relatedParties.map((r, index) => (
              <Grid
                container
                spacing={1}
                key={index}
                alignItems="center"
                style={{ marginBottom: "10px" }}
              >
                <Grid item xs={5}>
                  <TextField
                    fullWidth
                    label="Name"
                    value={r.name}
                    onChange={(e) =>
                      handleArrayChange("relatedParties", index, "name", e.target.value)
                    }
                  />
                </Grid>
                <Grid item xs={5}>
                  <TextField
                    fullWidth
                    label="Role"
                    value={r.role}
                    onChange={(e) =>
                      handleArrayChange("relatedParties", index, "role", e.target.value)
                    }
                  />
                </Grid>
                <Grid item xs={2}>
                  <IconButton
                    onClick={() => handleRemoveField("relatedParties", index)}
                    disabled={formData.relatedParties.length === 1}
                  >
                    <RemoveCircle />
                  </IconButton>
                </Grid>
              </Grid>
            ))}
            <Button
              startIcon={<AddCircle />}
              onClick={() =>
                handleAddField("relatedParties", { name: "", role: "" })
              }
            >
              Add Related Party
            </Button>
          </Grid>

          {/* Submit */}
          <Grid item xs={12}>
            <Button type="submit" variant="contained" color="primary">
              Save Product
            </Button>
          </Grid>
        </Grid>
      </form>
    </Paper>
  );
}

export default ProductForm;
