package com.comviva.resource;

import com.comviva.dto.ProductDTO;
import com.comviva.entity.Product;
import com.comviva.service.ProductService;
import com.comviva.service.mapper.ProductMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    private static final Logger log = LoggerFactory.getLogger(ProductResource.class);

    @Inject
    ProductMapper productMapper;

    @Inject
    ProductService productService;

    private final ObjectMapper mapper = new ObjectMapper();

    @GET
    public Response getProducts(@Context UriInfo uriInfo,
                                @QueryParam("page") @DefaultValue("0") int page,
                                @QueryParam("size") @DefaultValue("20") int size) {

        log.info("GET request works fine and Fetching the data");
        MultivaluedMap<String, String> allParams = uriInfo.getQueryParameters();
        log.debug("Query parameters: {}", allParams);

        List<Product> products = productService.dynamicSearch(allParams, page, size);

        String fieldsParam = allParams.getFirst("fields");
        if (fieldsParam != null && !fieldsParam.isBlank()) {
            log.info("Filtering response for the Products");
            List<String> requestedFields = Arrays.stream(fieldsParam.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());

            List<Map<String, Object>> out = products.stream().map(prod -> {
                Map<String, Object> fullMap = mapper.convertValue(prod, new TypeReference<Map<String, Object>>() {});
                Map<String, Object> filtered = new LinkedHashMap<>();
                for (String f : requestedFields) {
                    if (fullMap.containsKey(f)) {
                        filtered.put(f, fullMap.get(f));
                    } else if (f.contains(".")) {
                        String top = f.substring(0, f.indexOf('.'));
                        if (fullMap.containsKey(top)) {
                            filtered.putIfAbsent(top, fullMap.get(top));
                        }
                    }
                }
                return filtered;
            }).collect(Collectors.toList());

            log.info("Returning {} filtered products", out.size());
            return Response.ok(out).build();
        }

        List<ProductDTO> dtos = products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());

        log.info("Returning {} products", dtos.size());
        return Response.ok(dtos).build();
    }

    @POST
    public Response createProduct(@Valid ProductDTO productDTO) {
        log.info("POST /products called to create product: {}", productDTO.getName());

        try {
            Product product = productMapper.toEntity(productDTO);
            Product saved = productService.saveProduct(product);
            ProductDTO responseDTO = productMapper.toDTO(saved);
            log.info("Product created with name={}", saved.getName());
            return Response.status(Response.Status.CREATED).entity(responseDTO).build();

        } catch (Exception e) {
            log.error("Error creating product: {}", productDTO.getName(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }


    @PATCH
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") Long id, Map<String, Object> updates) {
        log.info("PATCH /products/{} called with updates={}", id, updates);
        try {
            ProductDTO updated = productService.updateProduct(id, updates);
            if (updated != null) {
                log.info("Product with id={} updated successfully", id);
                return Response.ok(updated).build();
            } else {
                log.warn("Product with id={} not found", id);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Product with id " + id + " not found")
                        .build();
            }
        } catch (Exception e) {
            log.error("Error updating product with id={}", id, e);
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") Long id) {
        log.info("DELETE /products/{} called", id);
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            log.info("Product with id={} deleted successfully", id);
            return Response.noContent().build();
        } else {
            log.warn("Product with id={} not found for deletion", id);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Product with id " + id + " not found")
                    .build();
        }
    }
}
