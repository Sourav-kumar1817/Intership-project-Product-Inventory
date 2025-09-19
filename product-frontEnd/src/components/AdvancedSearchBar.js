import React, { useState, useRef } from "react";
import { TextField, Button, Paper, Grid, Box, IconButton } from "@mui/material";
import { FilterList } from "@mui/icons-material";

function AdvancedSearchBar({ onSearch }) {
  const [quickSearch, setQuickSearch] = useState("");
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const [filters, setFilters] = useState({
    relatedParty: { role: "" },
    characteristic: { name: "", value: "" },
    status: { status: "" },
    productSpec: { name: "" }
  });

  const iconRef = useRef(null);
  const [dropdownPosition, setDropdownPosition] = useState({ top: 0, left: 0 });

  const handleFilterChange = (filterType, key, value) => {
    setFilters((prev) => ({
      ...prev,
      [filterType]: { ...prev[filterType], [key]: value }
    }));
  };

  const handleSearch = () => {
    const params = {};
    if (quickSearch) params[isNaN(quickSearch) ? "name" : "id"] = quickSearch;

    if (filters.relatedParty.role) params["relatedParties.role"] = filters.relatedParty.role;
    if (filters.characteristic.name && filters.characteristic.value) {
      params["characteristics.name"] = filters.characteristic.name;
      params["characteristics.value"] = filters.characteristic.value;
    }
    if (filters.status.status) params["status"] = filters.status.status;
    if (filters.productSpec.name) params["productSpecification.name"] = filters.productSpec.name;

    onSearch(params);

    // Reset all fields after search
    setQuickSearch("");
    setFilters({
      relatedParty: { role: "" },
      characteristic: { name: "", value: "" },
      status: { status: "" },
      productSpec: { name: "" }
    });
    setDropdownOpen(false);
  };

  const handleKeyPress = (e) => {
    if (e.key === "Enter") handleSearch();
  };

  const handleMouseEnter = () => {
    if (iconRef.current) {
      const rect = iconRef.current.getBoundingClientRect();
      setDropdownPosition({ top: rect.bottom + 5, left: rect.left });
    }
    setDropdownOpen(true);
  };

  const handleMouseLeave = () => {
    setDropdownOpen(false);
  };

  return (
    <Paper style={{ padding: 20, marginBottom: 20, position: "relative" }}>
      <Grid container spacing={1} alignItems="center">
        {/* Quick Search */}
        <Grid item xs={12} sm={6}>
          <TextField
            fullWidth
            placeholder="Search by Name or ID"
            value={quickSearch}
            onChange={(e) => setQuickSearch(e.target.value)}
            onKeyPress={handleKeyPress}
          />
        </Grid>

        {/* Filter Dropdown */}
        <Grid item xs={12} sm={3}>
          <Box
            ref={iconRef}
            onMouseEnter={handleMouseEnter}
            onMouseLeave={handleMouseLeave}
            style={{ display: "inline-block" }}
          >
            <IconButton>
              <FilterList />
            </IconButton>

            {dropdownOpen && (
              <Box
                style={{
                  position: "fixed",
                  top: dropdownPosition.top,
                  left: dropdownPosition.left,
                  width: 400,
                  background: "#fff",
                  border: "1px solid #ccc",
                  padding: 10,
                  zIndex: 1000,
                  boxShadow: "0px 4px 8px rgba(0,0,0,0.2)"
                }}
              >
                {/* Related Party */}
                <TextField
                  fullWidth
                  placeholder="Related Party Role"
                  value={filters.relatedParty.role}
                  onChange={(e) => handleFilterChange("relatedParty", "role", e.target.value)}
                  onKeyPress={handleKeyPress}
                  style={{ marginBottom: 10 }}
                />

                {/* Characteristic */}
                <Grid container spacing={1} style={{ marginBottom: 10 }}>
                  <Grid item xs={6}>
                    <TextField
                      fullWidth
                      placeholder="Characteristic Name"
                      value={filters.characteristic.name}
                      onChange={(e) => handleFilterChange("characteristic", "name", e.target.value)}
                      onKeyPress={handleKeyPress}
                    />
                  </Grid>
                  <Grid item xs={6}>
                    <TextField
                      fullWidth
                      placeholder="Characteristic Value"
                      value={filters.characteristic.value}
                      onChange={(e) => handleFilterChange("characteristic", "value", e.target.value)}
                      onKeyPress={handleKeyPress}
                    />
                  </Grid>
                </Grid>

                {/* Status */}
                <TextField
                  fullWidth
                  placeholder="Status"
                  value={filters.status.status}
                  onChange={(e) => handleFilterChange("status", "status", e.target.value)}
                  onKeyPress={handleKeyPress}
                  style={{ marginBottom: 10 }}
                />

                {/* Product Spec */}
                <TextField
                  fullWidth
                  placeholder="Product Spec Name"
                  value={filters.productSpec.name}
                  onChange={(e) => handleFilterChange("productSpec", "name", e.target.value)}
                  onKeyPress={handleKeyPress}
                />
              </Box>
            )}
          </Box>
        </Grid>

        {/* Search Button */}
        <Grid item xs={12} sm={3}>
          <Button variant="contained" color="primary" fullWidth onClick={handleSearch}>
            Search
          </Button>
        </Grid>
      </Grid>
    </Paper>
  );
}

export default AdvancedSearchBar;
