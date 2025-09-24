package com.comviva.service;

import com.comviva.dto.ProductDTO;
import com.comviva.entity.Product;
import jakarta.ws.rs.core.MultivaluedMap;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<Product> dynamicSearch(MultivaluedMap<String, String> queryParams, int page, int size);
    Product saveProduct(Product product);
    ProductDTO updateProduct(Long id, Map<String, Object> updates);
    boolean deleteProduct(Long id);
}
