package com.indian.indian.service;

import com.indian.indian.entity.BlogPost;
import com.indian.indian.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogServices {

    @Autowired
    private BlogRepository blogRepository;

    // Create or update a blog post
    public BlogPost saveBlogPost(BlogPost blogPost) {
        return blogRepository.save(blogPost);
    }

    // Retrieve all blog posts
    public List<BlogPost> getAllBlogPosts() {
        return blogRepository.findAll();
    }

    // Find a blog post by ID
    public Optional<BlogPost> getBlogPostById(Long id) {
        return blogRepository.findById(id);
    }

    // Find a blog post by slug
    public Optional<BlogPost> getBlogPostBySlug(String slug) {
        return blogRepository.findBySlug(slug);
    }

    // Find a blog post by slug
    public boolean existbySlug(String slug) {
        return blogRepository.existsBySlug(slug);
    }

    // Find a blog post by id
    public boolean existbyId(Long id) {
        return blogRepository.existsById(id);
    }

    // Delete a blog post by ID
    public void deleteBlogPost(Long id) {
        blogRepository.deleteById(id);
    }

    // // Get blog posts by category in ascending order
    // public List<BlogPost> getPostsByCategoryAscending(Long categoryId) {
    // return blogRepository.findByCategoryIdOrderByCreatedAtAsc(categoryId);
    // }

    // // Get blog posts by category in descending order
    // public List<BlogPost> getPostsByCategoryDescending(Long categoryId) {
    // return blogRepository.findByCategoryIdOrderByCreatedAtDesc(categoryId);
    // }
}
