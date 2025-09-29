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
        product.setProductSerialNumber("SN12345");
        product.setStatus("ACTIVE");

        ProductCharacteristic characteristic = new ProductCharacteristic();
        characteristic.setName("RAM");
        characteristic.setValue("16GB");
        product.setCharacteristics(new ArrayList<>(List.of(characteristic)));

        ProductOrderItem orderItem = new ProductOrderItem();
        product.setOrderItems(new ArrayList<>(List.of(orderItem)));

        RelatedParty relatedParty = new RelatedParty();
        relatedParty.setName("Vendor1");
        product.setRelatedParties(new ArrayList<>(List.of(relatedParty)));

        doNothing().when(em).persist(product);

        Product saved = productService.saveProduct(product);

        assertNotNull(saved);
        assertEquals("Laptop", saved.getName());
        assertEquals(1, saved.getCharacteristics().size());
        assertEquals(1, saved.getOrderItems().size());
        assertEquals(1, saved.getRelatedParties().size());
        verify(em, times(1)).persist(product);
    }

    // --- updateProduct full update ---
    @Test
    void testUpdateProduct_FullUpdate() {
        Product existing = new Product();
        existing.id = 1L;
        existing.setName("Old Product");
        existing.setDescription("Old Desc");
        existing.setStatus("ACTIVE");
        existing.setProductSerialNumber("SN-111");

        when(em.find(Product.class, 1L)).thenReturn(existing);
        when(em.merge(existing)).thenReturn(existing);

        ProductDTO mappedDto = new ProductDTO();
        mappedDto.setName("New Product");
        mappedDto.setDescription("New Desc");
        mappedDto.setStatus("INACTIVE");
        mappedDto.setProductSerialNumber("SN-222");
//        mappedDto.setIsBundle(true);
//        mappedDto.setIsCustomerVisible(false);

        when(productMapper.toDTO(existing)).thenReturn(mappedDto);

        Map<String, Object> updates = new HashMap<>();
        updates.put("name", "New Product");
        updates.put("description", "New Desc");
        updates.put("status", "INACTIVE");
        updates.put("productSerialNumber", "SN-222");
        updates.put("isBundle", true);
        updates.put("isCustomerVisible", false);

        ProductDTO result = productService.updateProduct(1L, updates);

        assertNotNull(result);
        assertEquals("New Product", result.getName());
        assertEquals("New Desc", result.getDescription());
        assertEquals("INACTIVE", result.getStatus());
        assertEquals("SN-222", result.getProductSerialNumber());
//        assertTrue(result.getIsBundle());
//        assertFalse(result.getIsCustomerVisible());

        verify(em, times(1)).merge(existing);
    }

    // --- updateProduct partial update ---
    @Test
    void testUpdateProduct_PartialUpdate() {
        Product existing = new Product();
        existing.id = 2L;
        existing.setName("Keep Name");
        existing.setDescription("Keep Desc");
        existing.setStatus("ACTIVE");
        existing.setProductSerialNumber("SN-333");

        when(em.find(Product.class, 2L)).thenReturn(existing);
        when(em.merge(existing)).thenReturn(existing);

        ProductDTO mappedDto = new ProductDTO();
        mappedDto.setName("Partial Update");
        mappedDto.setDescription("Keep Desc");
        mappedDto.setStatus("ACTIVE");
        mappedDto.setProductSerialNumber("SN-333");
//        mappedDto.setIsBundle(false);
//        mappedDto.setIsCustomerVisible(true);

        when(productMapper.toDTO(existing)).thenReturn(mappedDto);

        Map<String, Object> updates = new HashMap<>();
        updates.put("name", "Partial Update"); // only name updated

        ProductDTO result = productService.updateProduct(2L, updates);

        assertNotNull(result);
        assertEquals("Partial Update", result.getName());
        assertEquals("Keep Desc", result.getDescription());
        assertEquals("ACTIVE", result.getStatus());
        assertEquals("SN-333", result.getProductSerialNumber());

        verify(em, times(1)).merge(existing);
    }

    @Test
    void testUpdateProduct_NotFound() {
        when(em.find(Product.class, 10L)).thenReturn(null);
        assertThrows(ProductNotFoundException.class,
                () -> productService.updateProduct(10L, Map.of("name", "DoesNotExist")));
    }

    // --- deleteProduct ---
    @Test
    void testDeleteProduct() {
        Product product = new Product();
        when(em.find(Product.class, 1L)).thenReturn(product);
        doNothing().when(em).remove(product);
        boolean result = productService.deleteProduct(1L);
        assertTrue(result);
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

    // --- dynamicSearch ---
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

    @Test
    void testDynamicSearch_InvalidKey() {
        MultivaluedHashMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add("invalidKey", "value");

        assertThrows(InvalidSearchKeyException.class,
                () -> productService.dynamicSearch(queryParams, 0, 10));
    }

    @Test
    void testDynamicSearch_PaginationEdge() {
        MultivaluedHashMap<String, String> queryParams = new MultivaluedHashMap<>();
        queryParams.add("name", "Test");

        when(root.get("name")).thenReturn(path);
        when(path.as(String.class)).thenReturn(expression);
        when(cb.lower(expression)).thenReturn(expression);
        when(cb.like(expression, "%test%")).thenReturn(predicate);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        List<Product> results = productService.dynamicSearch(queryParams, -1, 0);

        assertNotNull(results);
        assertEquals(0, results.size());
        verify(typedQuery, never()).setFirstResult(anyInt());
        verify(typedQuery, never()).setMaxResults(anyInt());
    }

    @Test
    void testDynamicSearch_NullParams() {
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());
        List<Product> results = productService.dynamicSearch(new MultivaluedHashMap<>(), 0, 10);
        assertNotNull(results);
        verify(typedQuery, times(1)).getResultList();
    }
}