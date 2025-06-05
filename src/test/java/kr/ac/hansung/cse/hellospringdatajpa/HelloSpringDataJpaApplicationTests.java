package kr.ac.hansung.cse.hellospringdatajpa;

import jakarta.transaction.Transactional;
import kr.ac.hansung.cse.hellospringdatajpa.entity.Product;
import kr.ac.hansung.cse.hellospringdatajpa.repo.ProductRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class HelloSpringDataJpaApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(HelloSpringDataJpaApplicationTests.class);

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("테스트1: ID로 상품 찾기")
    public void findProductById() {

        // Optional 객체: null 값을 처리할 때 발생할 수 있는 NullPointerException을 방지
        // 값이 있으면 해당 값을 포함하고, 없으면 비어 있습니다.
        Optional<Product> product = productRepository.findById(1L);

        assertTrue(product.isPresent(), "상품이 존재해야 합니다");

        // 람다 표현식, parameters -> { statements; }, 익명 함수를 간결하게 작성할 수 있는 기능
        product.ifPresent(p -> {
            logger.info("상품 찾음: {}", p);
        });

    }

    @Test
    @DisplayName("테스트2: 모든 상품 찾기")
    public void findAllProducts() {

        List<Product> products = productRepository.findAll();
        assertNotNull(products);
        products.forEach(p -> logger.info("상품 찾음: {}", p));

    }

   @Test
    @DisplayName("테스트3: 상품 생성")
    public void createProduct() {

        Product product = new Product("OLED TV", "LG전자", "korea", 300.0);
        Product savedProduct = productRepository.save(product);

        Optional<Product> newProduct = productRepository.findById(savedProduct.getId());
        assertTrue(newProduct.isPresent(), "상품이 존재해야 합니다");

        newProduct.ifPresent(p -> {
            logger.info("상품 생성됨: {}", p);
            assertEquals("OLED TV", p.getName());
        });
    }

    @Test
    @DisplayName("테스트4: 이름으로 상품 찾기")
    public void findByName() {

        Product product = productRepository.findByName("Galaxy S21");
        assertEquals("Galaxy S21", product.getName());
    }

    @Test
    @DisplayName("테스트5: 이름 포함 및 페이징으로 상품 찾기")
    public void findByNameContainingWithPaging() {

        Pageable paging = PageRequest.of(0, 3);
        List<Product> productList = productRepository.findByNameContaining("MacBook", paging);

        logger.info("====findByNameContainingWithPaging: MacBook=====");
        productList.forEach(product -> logger.info("--> {}", product));

        assertEquals(3, productList.size(), "'MacBook'을 포함하는 상품 3개가 예상됩니다");

    }

    @Test
    @DisplayName("테스트6: 이름 포함, 페이징 및 정렬로 상품 찾기")
    public void findByNameContainingWithPagingAndSort( ) {

        Pageable paging = PageRequest.of(0, 3, Sort.Direction.DESC, "id");

        List<Product> productList =
                productRepository.findByNameContaining("Galaxy", paging);

        logger.info("===findByNameContainingWithPagingAndSort: Galaxy====");
        productList.forEach(product -> logger.info("--> {}", product));

        assertEquals(3, productList.size(), "'Galaxy'를 포함하는 상품 3개가 예상됩니다");

    }

    @Test
    @DisplayName("테스트7: 쿼리를 사용하여 이름으로 상품 검색")
    public void searchByNameUsingQuery() {
        List<Product> productList= productRepository.searchByName("Air");

        logger.info("====searchByNameUsingQuery: Air====");
        productList.forEach(product -> logger.info("--> {}", product));

        assertEquals(6, productList.size(), "'Air'를 포함하는 상품 6개가 예상됩니다");
    }
}
