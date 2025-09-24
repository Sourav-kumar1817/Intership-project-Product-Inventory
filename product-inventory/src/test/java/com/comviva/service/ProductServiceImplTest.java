package com.comviva.service;

import com.comviva.dto.ProductDTO;
import com.comviva.entity.*;
import com.comviva.exception.InvalidSearchKeyException;
import com.comviva.exception.ProductNotFoundException;
import com.comviva.service.mapper.ProductMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.ws.rs.core.MultivaluedHashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private EntityManager em;

    @Mock
    private ProductMapper productMapper;
    // Criteria API mocks
    @Mock private CriteriaBuilder cb;
    @Mock private CriteriaQuery<Product> cq;
    @Mock private Root<Product> root;
    @Mock private Path<Object> path;
    @Mock private Predicate predicate;
    @Mock private Expression<String> expression;
    @Mock private TypedQuery<Product> typedQuery;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(em.getCriteriaBuilder()).thenReturn(cb);
        when(cb.createQuery(Product.class)).thenReturn(cq);
        when(cq.from(Product.class)).thenReturn(root);
        when(cq.select(root)).thenReturn(cq);
        when(cq.distinct(true)).thenReturn(cq);
        when(em.createQuery(Mockito.<CriteriaQuery<Product>>any())).thenReturn(typedQuery);
    }
    // --- saveProduct ---
    @Test
    void testSaveProduct() {
        Product product = new Product();
        product.setName("Laptop");

        doNothing().when(em).persist(product);

        Product saved = productService.saveProduct(product);

        assertNotNull(saved);
        assertEquals("Laptop", saved.getName());
        verify(em, times(1)).persist(product);
    }
    // --- updateProduct ---
    @Test
    void testUpdateProduct() {
        Product product = new Product();
        product.setName("Original");

        Map<String, Object> updates = new HashMap<>();
        updates.put("name", "Updated");

        when(em.find(Product.class, 1L)).thenReturn(product);

        ProductDTO dto = new ProductDTO();
        dto.setName("Updated");
        when(productMapper.toDTO(product)).thenReturn(dto);

        ProductDTO updated = productService.updateProduct(1L, updates);

        assertNotNull(updated);
        assertEquals("Updated", updated.getName());
        verify(em, times(1)).find(Product.class, 1L);
        verify(em, times(1)).merge(product);
        verify(productMapper, times(1)).toDTO(product);
    }
    @Test
    void testUpdateProduct_NotFound() {
        when(em.find(Product.class, 1L)).thenReturn(null);
        assertThrows(ProductNotFoundException.class,
                () -> productService.updateProduct(1L, Map.of("name", "Test")));
    }
    // --- deleteProduct ---
    @Test
    void testDeleteProduct() {
        Product product = new Product();
        when(em.find(Product.class, 1L)).thenReturn(product);
        doNothing().when(em).remove(product);
        boolean result = productService.deleteProduct(1L);
        assertTrue(result);
        verify(em, times(1)).find(Product.class, 1L);
        verify(em, times(1)).remove(product);
    }
    @Test
    void testDeleteProduct_NotFound() {
        when(em.find(Product.class, 1L)).thenReturn(null);
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(1L));
    }
    @Test
    void testDeleteProduct_NullId() {
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(null));
    }
    // --- dynamicSearch Success ---
    @Test
    void testDynamicSearch_Success() {
        MultivaluedHashMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add("name", "Test");

        when(root.get("name")).thenReturn(path);
        when(path.as(String.class)).thenReturn(expression);
        when(cb.lower(expression)).thenReturn(expression);
        when(cb.like(expression, "%test%")).thenReturn(predicate);
        when(typedQuery.getResultList()).thenReturn(List.of(new Product()));

        List<Product> results = productService.dynamicSearch(queryParams, 0, 10);

        assertNotNull(results);
        assertEquals(1, results.size());
        verify(typedQuery, times(1)).setFirstResult(0);
        verify(typedQuery, times(1)).setMaxResults(10);
        verify(typedQuery, times(1)).getResultList();
    }

    // --- dynamicSearch InvalidKey ---
    @Test
    void testDynamicSearch_InvalidKey() {
        MultivaluedHashMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add("invalidKey", "value");

        assertThrows(InvalidSearchKeyException.class,
                () -> productService.dynamicSearch(queryParams, 0, 10));
    }

    // --- dynamicSearch Pagination Edge ---
    @Test
    void testDynamicSearch_PaginationEdge() {
        MultivaluedHashMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add("name", "Test");

        when(root.get("name")).thenReturn(path);
        when(path.as(String.class)).thenReturn(expression);
        when(cb.lower(expression)).thenReturn(expression);
        when(cb.like(expression, "%test%")).thenReturn(predicate);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        // Negative page/size should skip pagination
        List<Product> results = productService.dynamicSearch(queryParams, -1, 0);

        assertNotNull(results);
        assertEquals(0, results.size());
        verify(typedQuery, never()).setFirstResult(anyInt());
        verify(typedQuery, never()).setMaxResults(anyInt());
    }

    // --- dynamicSearch Null queryParams ---
    @Test
    void testDynamicSearch_NullParams() {
        List<Product> results = productService.dynamicSearch(new MultivaluedHashMap<>(), 0, 10);
        assertNotNull(results);
        verify(typedQuery, times(1)).setFirstResult(0);
        verify(typedQuery, times(1)).setMaxResults(10);
        verify(typedQuery, times(1)).getResultList();
    }
}
