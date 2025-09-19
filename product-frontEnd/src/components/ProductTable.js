import React from "react";
import {
  Table, TableBody, TableCell, TableContainer,
  TableHead, TableRow, Paper, IconButton
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";

const ProductTable = ({ products, onDelete }) => {
  return (
    <TableContainer component={Paper}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Product ID</TableCell>
            <TableCell>Product Name</TableCell>
            <TableCell>Status</TableCell>
            <TableCell>Characteristics</TableCell>
            <TableCell>Related Party</TableCell>
            <TableCell>Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {products.map((product) => (
            <TableRow key={product.id}>
              <TableCell>{product.id}</TableCell>
              <TableCell>{product.name}</TableCell>
              <TableCell>{product.status}</TableCell>
              <TableCell>{product.characteristics}</TableCell>
              <TableCell>{product.relatedParty}</TableCell>
              <TableCell>
                <IconButton color="error" onClick={() => onDelete(product.id)}>
                  <DeleteIcon />
                </IconButton>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default ProductTable;
