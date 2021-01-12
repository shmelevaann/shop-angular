package ru.chiffa.reposirories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chiffa.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
