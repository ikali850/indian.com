package com.indian.indian.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.indian.indian.entity.BlogPost;

@Repository
public interface BlogRepository extends JpaRepository<BlogPost, Long> {

    Optional<BlogPost> findBySlug(String slug);

    // List<BlogPost> findByCategoryIdOrderByCreatedAtAsc(Long categoryId);

    // List<BlogPost> findByCategoryIdOrderByCreatedAtDesc(Long categoryId);

    boolean existsBySlug(String slug);

    @Query(value = "SELECT * FROM blog_posts WHERE categories LIKE CONCAT('%', :category, '%')", nativeQuery = true)
    List<BlogPost> findByCategory(@Param("category") String category);

}
