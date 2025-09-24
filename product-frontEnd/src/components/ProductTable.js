import React from "react";
import {
  Table, TableBody, TableCell, TableContainer,
  TableHead, TableRow, Paper, IconButton, Tooltip, Typography
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";

const ProductTable = ({ products, mode = "search", onDelete, onEdit }) => {
  // Function to safely convert array/object to string
  const formatContent = (data, isRelated = false) => {
    if (!data) return "";
    if (Array.isArray(data)) {
      return data
        .map(d =>
          typeof d === "string"
            ? d
            : isRelated
            ? `${d.name || ""} (${d.role || ""})`
            : `${d.name || ""}: ${d.value || ""} (${d.valueType || ""})`
        )
        .join(", ");
    }
    return data;
  };

  return (
    <TableContainer component={Paper} sx={{ backgroundColor: "#D6D3D1" }}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Product ID</TableCell>
            <TableCell>Product Name</TableCell>
            <TableCell>Status</TableCell>
            <TableCell>Characteristics</TableCell>
            <TableCell>Related Party</TableCell>
            {mode === "edit" && <TableCell>Actions</TableCell>}
          </TableRow>
        </TableHead>
        <TableBody>
          {products.map((product) => {
            const characteristics = formatContent(product.characteristics);
            const relatedParties = formatContent(product.relatedParties, true);

            return (
              <TableRow key={product.id}>
                <TableCell>{product.id}</TableCell>
                <TableCell>{product.name}</TableCell>
                <TableCell>{product.status}</TableCell>

                {/* Tooltip for long characteristics */}
                <TableCell>
                  <Tooltip title={characteristics} arrow placement="top-start">
                    <Typography
                      variant="body2"
                      sx={{
                        whiteSpace: "nowrap",
                        overflow: "hidden",
                        textOverflow: "ellipsis",
                        maxWidth: 250, // adjust width as needed
                        cursor: "pointer",
                      }}
                    >
                      {characteristics}
                    </Typography>
                  </Tooltip>
                </TableCell>

                {/* Tooltip for related parties */}
                <TableCell>
                  <Tooltip title={relatedParties} arrow placement="top-start">
                    <Typography
                      variant="body2"
                      sx={{
                        whiteSpace: "nowrap",
                        overflow: "hidden",
                        textOverflow: "ellipsis",
                        maxWidth: 200, // adjust width as needed
                        cursor: "pointer",
                      }}
                    >
                      {relatedParties}
                    </Typography>
                  </Tooltip>
                </TableCell>

                {mode === "edit" && (
                  <TableCell>
                    <IconButton color="primary" onClick={() => onEdit(product)}>
                      <EditIcon />
                    </IconButton>
                    <IconButton color="error" onClick={() => onDelete(product.id)}>
                      <DeleteIcon />
                    </IconButton>
                  </TableCell>
                )}
              </TableRow>
            );
          })}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default ProductTable;
