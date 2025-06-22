package com.smartbazaar.productservice.controller;

import com.smartbazaar.productservice.model.Product;
import com.smartbazaar.productservice.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/list")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }
}
