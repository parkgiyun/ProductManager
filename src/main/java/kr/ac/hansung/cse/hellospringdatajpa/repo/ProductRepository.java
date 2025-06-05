package kr.ac.hansung.cse.hellospringdatajpa.repo;

import kr.ac.hansung.cse.hellospringdatajpa.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
    List<Product> findByNameContaining(String searchKeyword, Pageable paging);

    // Product 엔티티에서 이름 속성에 특정 부분 문자열이 포함된 레코드를 검색하는 데 사용됨
    // JPQL(Java Persistence Query Language)을 사용한 사용자 정의 쿼리, %는 0개 이상 문자와 일치
    @Query("select p from Product p where p.name like %?1%")
    List<Product> searchByName(String name);
}
