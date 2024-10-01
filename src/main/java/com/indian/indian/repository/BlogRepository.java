package com.indian.indian.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.indian.indian.entity.BlogPost;

@Repository
public interface BlogRepository extends JpaRepository<BlogPost, Long> {

    Optional<BlogPost> findBySlug(String slug);

    // List<BlogPost> findByCategoryIdOrderByCreatedAtAsc(Long categoryId);

    // List<BlogPost> findByCategoryIdOrderByCreatedAtDesc(Long categoryId);

    boolean existsBySlug(String slug);

}
