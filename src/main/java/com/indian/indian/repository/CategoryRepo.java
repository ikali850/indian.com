package com.indian.indian.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.indian.indian.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    boolean existsById(Long id);

    boolean existsByName(String name);

}
