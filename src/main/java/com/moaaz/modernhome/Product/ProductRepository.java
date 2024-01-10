package com.moaaz.modernhome.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findAllByNameContaining(String text);

    public List<Product> findAllByDetailsContaining(String text);

    public List<Product> findAllByIsDeleted(boolean isDeleted);

    @Query("Delete From Product p Where p.id = :productId")
    public void customDeleteById(@Param("productId") long productId);


    Optional<Product> findByIdAndIsDeleted(long productId, boolean isDeleted);
}
