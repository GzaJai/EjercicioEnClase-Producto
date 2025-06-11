package org.gestionproductos.service;

import org.gestionproductos.model.Product;
import org.gestionproductos.repository.ProductRepository;
import java.util.Optional;


public class ProductService {
    private ProductRepository productRepository;
    public ProductService(ProductRepository
                                  productRepository) {
        this.productRepository = productRepository;
    }
    public Optional getProductById(Long id) {
        return productRepository.findById(id);
    }
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
}
