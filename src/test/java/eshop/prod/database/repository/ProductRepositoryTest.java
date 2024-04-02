package eshop.prod.database.repository;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import eshop.prod.database.entities.Product;
import eshop.prod.database.repository.models.RepositoryTestParent;

@SuppressWarnings("null")
class ProductRepositoryTest extends RepositoryTestParent {

    @Autowired
    private ProductRepository productRepository;

    Product product1;
    Product product2;

    @BeforeEach
    void setUpBeforeClass() {
        product1 = Product.builder()
                .name("product1")
                .price(1.0)
                .description("description1")
                .brand("brand1")
                .image("image1")
                .stock(10)
                .build();

        product2 = Product.builder()
                .name("product2")
                .price(2.0)
                .description("description2")
                .brand("brand2")
                .image("image2")
                .stock(20)
                .build();
    }

    @AfterEach
    void setUp() {
        productRepository.flush();
        productRepository.deleteAll();
    }

    //crud

    //create
    @Test
    void testSave() {
        Product saved = productRepository.save(product1);
        Product found = productRepository.findAll().get(0);
        assertEquals(saved, found);
    }

    //read by id
    @Test
    void testFindById() {
        Product saved = productRepository.save(product1);
        Product found = productRepository.findById(saved.getId_product()).get();
        assertEquals(saved, found);
    }

    // read
    @Test
    void testFindAll() {
        productRepository.save(product1);
        productRepository.save(product2);
        assertEquals(2, productRepository.count());
    }


    // update
    @Test
    void testUpdate() {
        Product saved = productRepository.save(product1);
        saved.setDescription("description2");
        Product updated = productRepository.save(saved);
        assertEquals("description2", updated.getDescription());
    }


    // delete
    @Test
    void testDeleteById() {
        Product saved = productRepository.save(product1);
        assertEquals(1, productRepository.count());
        productRepository.deleteById(saved.getId_product());
        assertEquals(0, productRepository.count());
    }

    // aditional

    @Test
    void testFindBySearchTerm() {
        productRepository.save(product1);
        productRepository.save(product2);
        assertEquals(2, productRepository.findBySearchTerm("product").get().size());
    }

    @Test
    void testFindByStockGreaterThanZero() {
        productRepository.save(product1);
        productRepository.save(product2);
        assertEquals(2, productRepository.findByStockGreaterThanZero().get().size());
    }

    @Test
    void testFindByPriceLessThanAndStockGreaterThan() {
        productRepository.save(product1);
        productRepository.save(product2);
        assertEquals(1, productRepository.findByPriceAndStockLessThan(2.0, 10).get().size());
    }
}
