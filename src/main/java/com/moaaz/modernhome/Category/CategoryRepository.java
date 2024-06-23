package com.moaaz.modernhome.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category , Long> {

    @Query(value = "SELECT * FROM `category` WHERE name LIKE :text% or details LIKE :text%" , nativeQuery = true)
    public List<Category> findAllByNameContainingOrDetailsContaining(String text);
}
