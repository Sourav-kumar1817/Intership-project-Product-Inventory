package com.comviva.service;

import com.comviva.entity.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.MultivaluedMap;

import java.util.*;

@ApplicationScoped
public class ProductService {

    @Inject
    EntityManager em;
    private static final Set<String> ALLOWED_SEARCH_KEYS = Set.of(
            "id",
            "name",
            "description",
            "status",
            "productSerialNumber",
            "characteristics.name",
            "characteristics.value",
            "relatedParties.name",
            "orderItems.externalId",
            "orderItems.id",
            "orderItems.name",
            "realizingServices.name",
            "relatedParties.role"
    );
    public List<Product> dynamicSearch(MultivaluedMap<String, String> queryParams, int page, int size) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        cq.select(root).distinct(true);

        Map<String, From<?, ?>> joins = new HashMap<>();
        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : queryParams.entrySet()) {
            String rawKey = entry.getKey();
            if ("fields".equals(rawKey) || "page".equals(rawKey) || "size".equals(rawKey)) continue;
            if (!isAllowed(rawKey)) {
                continue;
            }

            List<String> values = entry.getValue();
            if (values == null || values.isEmpty())  continue;
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
        return typedQuery.getResultList();
    }
    private boolean isAllowed(String rawKey) {
        if (ALLOWED_SEARCH_KEYS.contains(rawKey)) return true;
        return false;
    }
    private Path<?> resolvePath(Root<Product> root, String dotted, Map<String, From<?, ?>> joins) {
        String[] parts = dotted.split("\\.");
        if (parts.length == 1) {
            return root.get(parts[0]);
        }

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

        if (product.characteristics != null) {
            for (var c : product.characteristics) {
                c.product = product;
            }
        }
        if (product.orderItems != null) {
            for (var o : product.orderItems) {
                o.product = product;
            }
        }
        if (product.relatedParties != null) {
            for (var r : product.relatedParties) {
                r.product = product;
            }
        }
        if (product.realizingServices != null) {
            for (var s : product.realizingServices) {
                s.product = product;
            }
        }
        em.persist(product);
        return product;
    }

    @Transactional
    public boolean deleteProduct(Long id) {
        Product product = em.find(Product.class, id);
        if (product != null) {
            em.remove(product);
            return true;
        }
        return false;
    }


}
