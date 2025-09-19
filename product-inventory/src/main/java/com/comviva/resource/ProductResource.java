package com.comviva.resource;

import com.comviva.dto.ProductDTO;
import com.comviva.entity.Product;
import com.comviva.service.ProductService;
import com.comviva.service.mapper.ProductMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.*;
import java.util.stream.Collectors;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductService productService;

    private final ObjectMapper mapper = new ObjectMapper();
    @GET
    public Response getProducts(@Context UriInfo uriInfo,
                                @QueryParam("page") @DefaultValue("0") int page,
                                @QueryParam("size") @DefaultValue("20") int size) {

        MultivaluedMap<String, String> allParams = uriInfo.getQueryParameters();
        List<Product> products = productService.dynamicSearch(allParams, page, size);
        String fieldsParam = allParams.getFirst("fields");
        if (fieldsParam != null && !fieldsParam.isBlank()) {
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

            return Response.ok(out).build();
        }
        List<ProductDTO> dtos = products.stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());

        return Response.ok(dtos).build();
    }
    @POST
    public Response createProduct(Product product) {
        try {
            Product saved = productService.saveProduct(product);
            return Response.status(Response.Status.CREATED).entity(saved).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") Long id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Product with id " + id + " not found")
                    .build();
        }
    }


}
