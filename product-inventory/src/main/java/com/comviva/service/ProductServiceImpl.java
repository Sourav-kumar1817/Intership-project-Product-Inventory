package com.comviva.service;

import com.comviva.entity.*;
import com.comviva.exception.InvalidProductException;
import com.comviva.exception.InvalidSearchKeyException;
import com.comviva.exception.ProductNotFoundException;
import com.comviva.service.mapper.ProductMapper;
import com.comviva.dto.ProductDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.core.MultivaluedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@ApplicationScoped
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Inject
    ProductMapper productMapper;

    @Inject
    EntityManager em;

    private static final Set<String> ALLOWED_SEARCH_KEYS = Set.of(
            "id", "name", "description", "status", "productSerialNumber",
            "characteristics.name", "characteristics.value", "relatedParties.name",
            "orderItems.externalId", "orderItems.id", "orderItems.name",
            "realizingServices.name", "relatedParties.role"
    );

    @Override
    public List<Product> dynamicSearch(MultivaluedMap<String, String> queryParams, int page, int size) {
        log.info("Executing dynamicSearch with page={} and size={}", page, size);

        if (queryParams == null) {
            queryParams = new jakarta.ws.rs.core.MultivaluedHashMap<>();
        }

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        cq.select(root).distinct(true);

        Map<String, From<?, ?>> joins = new HashMap<>();
        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : queryParams.entrySet()) {
            String rawKey = entry.getKey();
            if ("fields".equals(rawKey) || "page".equals(rawKey) || "size".equals(rawKey)) continue;
            if (!isAllowed(rawKey)) throw new InvalidSearchKeyException("Invalid Search fields " + rawKey);

            List<String> values = entry.getValue();
            if (values == null || values.isEmpty()) continue;

            try {
                Path<?> path = resolvePath(root, rawKey, joins);
                List<Predicate> orPreds = new ArrayList<>();
                for (String v : values) {
                    if (v == null) continue;
                    Expression<String> expr = path.as(String.class);
                    orPreds.add(cb.like(cb.lower(expr), "%" + v.toLowerCase() + "%"));
                }
                if (!orPreds.isEmpty()) {
                    predicates.add(cb.or(orPreds.toArray(new Predicate[0])));
                }
            } catch (IllegalArgumentException ex) {
                log.error("Error resolving path for key '{}'", rawKey, ex);
                throw new InvalidSearchKeyException("Invalid search key or path " + rawKey);
            }
        }
        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
        }
        TypedQuery<Product> typedQuery = em.createQuery(cq);
        if (page >= 0 && size > 0) {
            typedQuery.setFirstResult(page * size);
            typedQuery.setMaxResults(size);
        }
        List<Product> results = typedQuery.getResultList();
        log.info("dynamicSearch returned {} products", results.size());
        return results;
    }
    private boolean isAllowed(String rawKey) {
        return ALLOWED_SEARCH_KEYS.contains(rawKey);
    }
    private Path<?> resolvePath(Root<Product> root, String dotted, Map<String, From<?, ?>> joins) {
        String[] parts = dotted.split("\\.");
        if (parts.length == 1) return root.get(parts[0]);

        From<?, ?> current = root;
        StringBuilder joinPath = new StringBuilder();
        for (int i = 0; i < parts.length - 1; i++) {
            if (joinPath.length() > 0) joinPath.append('.');
            joinPath.append(parts[i]);
            String key = joinPath.toString();
            From<?, ?> join = joins.get(key);
            if (join == null) {
                join = current.join(parts[i], JoinType.LEFT);
                joins.put(key, join);
            }
            current = join;
        }
        return current.get(parts[parts.length - 1]);
    }
@Transactional
public Product saveProduct(Product product) {
    log.info("Saving product: {}", product.getName());
    // Validate required fields
    if (product.getName() == null || product.getName().isBlank()) {
        throw new InvalidProductException("Product name cannot be blank");
    }
    // Initialize lists if null to avoid runtime errors
    if (product.getCharacteristics() == null) product.setCharacteristics(new ArrayList<>());
    if (product.getOrderItems() == null) product.setOrderItems(new ArrayList<>());
    if (product.getRelatedParties() == null) product.setRelatedParties(new ArrayList<>());
    if (product.getRealizingServices() == null) product.setRealizingServices(new ArrayList<>());
    // Set parent reference for child entities
    product.getCharacteristics().forEach(c -> c.setProduct(product));
    product.getOrderItems().forEach(o -> o.setProduct(product));
    product.getRelatedParties().forEach(r -> r.setProduct(product));
    product.getRealizingServices().forEach(s -> s.setProduct(product));

    // Persist product
    em.persist(product);
    log.info("Product saved successfully with ");

    return product;
}
    @PATCH
    @Transactional
    public ProductDTO updateProduct(Long id, Map<String, Object> updates) {
        log.info("Updating product with id={}", id);
        Product product = em.find(Product.class, id);
        if (product == null) {
            throw new ProductNotFoundException("Product not found with id = " + id);
        }
        try {
            // Load collections
            if (product.getCharacteristics() != null) product.getCharacteristics().size();
            if (product.getRelatedParties() != null) product.getRelatedParties().size();
            if (product.getOrderItems() != null) product.getOrderItems().size();
            if (product.getRealizingServices() != null) product.getRealizingServices().size();
            // Update simple fields
            if (updates.containsKey("name")) {
                String name = (String) updates.get("name");
                if (name == null || name.isBlank()) throw new IllegalArgumentException("Blank Name Exception");
                product.setName(name);
            }

            if (updates.containsKey("description")) {
                String description = (String) updates.get("description");
                if (description == null || description.isBlank()) throw new IllegalArgumentException("Description Null Exception");
                product.setDescription(description);
            }

            if (updates.containsKey("status")) {
                String status = (String) updates.get("status");
                if (status == null || status.isBlank()) throw new IllegalArgumentException("Status Null Exception");
                product.setStatus(status);
            }

            if (updates.containsKey("productSerialNumber")) {
                String productSerialNumber = (String) updates.get("productSerialNumber");
                if (productSerialNumber == null || productSerialNumber.isBlank()) throw new IllegalArgumentException("Serial Blank Exception");
                product.setProductSerialNumber(productSerialNumber);
            }

            if (updates.containsKey("isBundle")) product.setIsBundle((Boolean) updates.get("isBundle"));
            if (updates.containsKey("isCustomerVisible")) product.setIsCustomerVisible((Boolean) updates.get("isCustomerVisible"));

            // Update collections (partial updates allowed)
            updateCharacteristics(product, updates);
            updateRelatedParties(product, updates);
            updateOrderItems(product, updates);
            updateRealizingServices(product, updates);

            em.merge(product);
            log.info("Product with id={} updated successfully", id);
            return productMapper.toDTO(product);

        } catch (Exception e) {
            log.error("Error updating product with id={}", id, e);
            throw e;
        }
    }
    private void updateCharacteristics(Product product, Map<String, Object> updates) {
        if (!updates.containsKey("characteristics")) return;
        List<Map<String, Object>> chars = (List<Map<String, Object>>) updates.get("characteristics");
        if (chars != null) {
            product.getCharacteristics().clear(); // clear only if new data is present
            for (Map<String, Object> c : chars) {
                ProductCharacteristic newChar = new ProductCharacteristic();
                newChar.setName((String) c.get("name"));
                newChar.setValueType((String) c.get("valueType"));
                newChar.setValue((String) c.get("value"));
                newChar.setProduct(product);
                product.getCharacteristics().add(newChar);
            }
        }
    }

    private void updateRelatedParties(Product product, Map<String, Object> updates) {
        if (!updates.containsKey("relatedParties")) return;
        List<Map<String, Object>> parties = (List<Map<String, Object>>) updates.get("relatedParties");
        if (parties != null) {
            product.getRelatedParties().clear(); // clear only if new data is present
            for (Map<String, Object> r : parties) {
                RelatedParty newParty = new RelatedParty();
                newParty.setName((String) r.get("name"));
                newParty.setRole((String) r.get("role"));
                newParty.setExternalId((String) r.get("externalId"));
                newParty.setHref((String) r.get("href"));
                newParty.setReferredType((String) r.get("referredType"));
                newParty.setProduct(product);
                product.getRelatedParties().add(newParty);
            }
        }
    }

    private void updateOrderItems(Product product, Map<String, Object> updates) {
        if (!updates.containsKey("orderItems")) return;
        List<Map<String, Object>> orders = (List<Map<String, Object>>) updates.get("orderItems");
        if (orders != null) {
            product.getOrderItems().clear(); // clear only if new data is present
            for (Map<String, Object> o : orders) {
                ProductOrderItem newOrder = new ProductOrderItem();
                newOrder.setProductOrderId((String) o.get("productOrderId"));
                newOrder.setProductOrderHref((String) o.get("productOrderHref"));
                newOrder.setOrderItemId((String) o.get("orderItemId"));
                newOrder.setOrderItemAction((String) o.get("orderItemAction"));
                newOrder.setRole((String) o.get("role"));
                newOrder.setAction((String) o.get("action"));
                newOrder.setProduct(product);
                product.getOrderItems().add(newOrder);
            }
        }
    }

    private void updateRealizingServices(Product product, Map<String, Object> updates) {
        if (!updates.containsKey("realizingServices")) return;
        List<Map<String, Object>> services = (List<Map<String, Object>>) updates.get("realizingServices");
        if (services != null) {
            product.getRealizingServices().clear(); // clear only if new data is present
            for (Map<String, Object> s : services) {
                RealizingService newService = new RealizingService();
                newService.setExternalId((String) s.get("externalId"));
                newService.setHref((String) s.get("href"));
                newService.setRole((String) s.get("role"));
                newService.setReferredType((String) s.get("referredType"));
                newService.setProduct(product);
                product.getRealizingServices().add(newService);
            }
        }
    }


    @Transactional
    public boolean deleteProduct(Long id) {
        if (id == null) throw new ProductNotFoundException("Product id cannot be null");

        Product product = em.find(Product.class, id);
        if (product != null) {
            em.remove(product);
            log.info("Product with id={} deleted successfully", id);
            return true;
        }
        throw new ProductNotFoundException("Product not found with id = " + id);
    }
}
