package eshop.prod.database.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import eshop.prod.database.entities.Product;
import eshop.prod.database.entities.dto.ProductDTO;
import eshop.prod.database.entities.mappers.ProductMapper;
import eshop.prod.database.repository.ProductRepository;

@SuppressWarnings("null")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;
    
    Product product1 = Product.builder()
            .name("product1")
            .price(1.0)
            .description("description1")
            .brand("brand1")
            .image("image1")
            .stock(10)
            .build();

    Product productRes = Product.builder()
            .id_product(1L)
            .name("product2")
            .price(2.0)
            .description("description2")
            .brand("brand2")
            .image("image2")
            .stock(20)
            .build();
    
    ProductDTO productDTO = ProductMapper.INSTANCE.productToProductDTO(product1);

    @BeforeEach
    void setUp() {
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(productRes);
        when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product1));
    }

    @Test
    void testCreateProduct() {
        ProductDTO saved = productService.createProduct(productDTO);
        assertNotNull(saved);
    }

    @Test
    void testDeleteProduct() {
        ProductDTO saved = productService.createProduct(productDTO);
        when(productRepository.count()).thenReturn(1L);
        assertEquals(1L, productRepository.count());
        productService.deleteProduct(saved.getId_product());
        when(productRepository.count()).thenReturn(0L);
        assertEquals(0L, productRepository.count());
    }

    @Test
    void testFindByNameContaining() {
        productService.createProduct(productDTO);
        when(productRepository.findBySearchTerm(Mockito.anyString())).thenReturn(Optional.of(List.of(product1)));
        List<ProductDTO> products = productService.findByNameContaining("product");
        assertEquals(1, products.size());
    }

    @Test
    void testFindByPriceLessThanAndStockGreaterThan() {
        productService.createProduct(productDTO);
        when(productRepository.findByPriceAndStockLessThan(Mockito.anyDouble(), Mockito.anyDouble())).thenReturn(Optional.of(List.of(product1)));
        List<ProductDTO> products = productService.findByPriceLessThanAndStockGreaterThan(1.0, 10.0);
        assertEquals(1, products.size());
    }

    @Test
    void testFindByStockGreaterThanZero() {
        productService.createProduct(productDTO);
        when(productRepository.findByStockGreaterThanZero()).thenReturn(Optional.of(List.of(product1)));
        List<ProductDTO> products = productService.findByStockGreaterThanZero();
        assertEquals(1, products.size());
    }

    @Test
    void testGetAllProducts() {
        productService.createProduct(productDTO);
        when(productRepository.findAll()).thenReturn(List.of(product1));
        List<ProductDTO> products = productService.getAllProducts();
        assertEquals(1, products.size());
    }

    @Test
    void testGetProductById() {
        ProductDTO saved = productService.createProduct(productDTO);
        when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product1));
        ProductDTO product = productService.getProductById(saved.getId_product());
        assertNotNull(product);
    }

    @Test
    void testUpdateProduct() {
        ProductDTO saved = productService.createProduct(productDTO);
        when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product1));
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(productRes);
        ProductDTO updated = productService.updateProduct(saved.getId_product(), productDTO);
        assertNotNull(updated);
    }
}
