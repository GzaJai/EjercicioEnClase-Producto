package org.gestionproductos.repository;

import org.gestionproductos.model.Product;
import java.util.Optional;


public interface ProductRepository {
    Optional findById(Long id);
    Product save(Product product);
}
