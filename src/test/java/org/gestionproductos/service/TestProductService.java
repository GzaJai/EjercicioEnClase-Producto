package org.gestionproductos.service;

import org.gestionproductos.model.Product;
import org.gestionproductos.repository.ProductRepository;
import org.gestionproductos.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @DisplayName("Probamos que retorne un producto por su ID")
    @Test
    void testGetProductById_ProductExists() {
        Long id = 1L;
        Product mockProduct = new Product(1L, "Mouse", 200);
        mockProduct.setId(id);

        System.out.println("🔍 Ejecutando test: buscar producto con ID existente (" + id + ")");

        when(productRepository.findById(id)).thenReturn(Optional.of(mockProduct));

        Optional<Product> result = productService.getProductById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(productRepository, times(1)).findById(id);

        System.out.println("✅ Resultado: se encontró el producto -> " +
                "Nombre: " + result.get().getName() +
                ", Precio: " + result.get().getPrice() + "\n");
    }


    @DisplayName("Probamos que retorne un producto que no existe")
    @Test
    void testGetProductById_ProductDoesNotExist() {
        Long id = 2L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        System.out.println("🔍 Ejecutando test: buscar producto con ID inexistente (" + id + ")");

        Optional<Product> result = productService.getProductById(id);

        assertFalse(result.isPresent());
        verify(productRepository, times(1)).findById(id);

        System.out.println("✅ Resultado: el producto no existe, se recibió Optional.empty" + "\n");
    }


    @DisplayName("Probamos crear un nuevo producto")
    @Test
    void testCreateProduct() {
        Product newProduct = new Product(1L, "Mouse", 200);

        when(productRepository.save(newProduct)).thenReturn(newProduct);

        Product result = productService.createProduct(newProduct);

        System.out.println("Producto creado: " + result.getName());

        assertNotNull(result);
        assertEquals("Mouse", result.getName());
    }

    @DisplayName("Probamos crear un producto sin pasarle nombre al constructor")
    @Test
    void testCreateProduct_MissingName() {
        System.out.println("⚠️ Ejecutando test: crear producto sin nombre");

        Product incompleteProduct = new Product();
        incompleteProduct.setId(10L);
        incompleteProduct.setPrice(100);

        when(productRepository.save(incompleteProduct)).thenReturn(incompleteProduct);

        Product result = productService.createProduct(incompleteProduct);

        System.out.println("🛠 Producto creado con datos incompletos: " + result);

        assertNotNull(result);
        assertNull(result.getName());
        verify(productRepository, times(1)).save(incompleteProduct);

        System.out.println("✅ Resultado: el producto fue creado, aunque con nombre nulo \n");
    }
}