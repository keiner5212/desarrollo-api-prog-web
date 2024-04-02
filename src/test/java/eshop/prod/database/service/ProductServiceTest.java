package eshop.prod.database.service;

import static org.junit.Assert.assertEquals;
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

    }

    @Test
    void testDeleteProduct() {

    }

    @Test
    void testFindByNameContaining() {

    }

    @Test
    void testFindByPriceLessThanAndStockGreaterThan() {

    }

    @Test
    void testFindByStockGreaterThanZero() {

    }

    @Test
    void testGetAllProducts() {

    }

    @Test
    void testGetProductById() {

    }

    @Test
    void testUpdateProduct() {

    }
}
