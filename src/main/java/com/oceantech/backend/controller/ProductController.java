package com.oceantech.backend.controller;

import com.oceantech.backend.entity.Product;
import com.oceantech.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProducts(
            @RequestParam(required = false) Long category,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "25") int limit,
            @RequestParam(defaultValue = "0") int offset) {

        List<Product> products = productRepository.findAll();

        if (filter != null && !filter.isEmpty()) {
            String f = filter.toLowerCase();
            products = products.stream()
                    .filter(p -> p.getName().toLowerCase().contains(f))
                    .toList();
        }

        if (category != null) {
            products = products.stream()
                    .filter(p -> p.getCategory() != null && p.getCategory().getId().equals(category))
                    .toList();
        }

        if (sort != null) {
            products = switch (sort) {
                case "price:asc" -> products.stream().sorted((a, b) -> Double.compare(a.getPrice(), b.getPrice())).toList();
                case "price:desc" -> products.stream().sorted((a, b) -> Double.compare(b.getPrice(), a.getPrice())).toList();
                case "rating:asc" -> products.stream().sorted((a, b) -> Double.compare(a.getRating(), b.getRating())).toList();
                case "rating:desc" -> products.stream().sorted((a, b) -> Double.compare(b.getRating(), a.getRating())).toList();
                default -> products;
            };
        }

        int total = products.size();
        products = products.stream().skip(offset).limit(limit).toList();

        Map<String, Object> response = new HashMap<>();
        response.put("total", total);
        response.put("products", products);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        if (product.getImages() != null) {
            product.getImages().forEach(img -> img.setProduct(product));
        }
        return ResponseEntity.ok(productRepository.save(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        product.setId(id);
        if (product.getImages() != null) {
            product.getImages().forEach(img -> img.setProduct(product));
        }
        return ResponseEntity.ok(productRepository.save(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}