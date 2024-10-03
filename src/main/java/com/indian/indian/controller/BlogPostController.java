package com.indian.indian.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.indian.indian.entity.BlogPost;
import com.indian.indian.entity.BlogPostRequestDTO;
import com.indian.indian.entity.Category;
import com.indian.indian.entity.User;
import com.indian.indian.service.BlogServices;
import com.indian.indian.service.CategoryService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BlogPostController {

    @Autowired
    private BlogServices blogServices;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin/manage-posts")
    public ModelAndView managePosts() {
        List<BlogPost> blogList = this.blogServices.getAllBlogPosts();
        System.out.println("blog data is :" + blogList);
        List<Category> categoryList = this.categoryService.getAllCategories();
        ModelAndView mv = new ModelAndView("master-admin/manage-post", HttpStatus.OK);
        mv.addObject("blogList", blogList);
        mv.addObject("categoryList", categoryList);
        return mv;
    }

    @PostMapping("/admin/blog/post")
    public ModelAndView savePost(@ModelAttribute BlogPostRequestDTO blogPostDto, HttpSession session,
            RedirectAttributes redirectAttributes) {
        // Get current user
        User currentUser = (User) session.getAttribute("user");
        try {
            this.blogServices.saveBlogPost(blogPostDto, currentUser);
            redirectAttributes.addFlashAttribute("success", "Post Created.."); // Success message
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Something went wrong.. Please try again!"); // Error message
        }
        return new ModelAndView("redirect:/admin/manage-posts"); // Redirect to manage-posts
    }

    @PostMapping("/admin/edit-post")
    public ModelAndView updatePost(@ModelAttribute BlogPostRequestDTO blogPostDto, HttpSession session,
            RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");

        try {
            this.blogServices.saveBlogPost(blogPostDto, user);
            redirectAttributes.addFlashAttribute("success", "Post Updated.."); // Success message
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Something went wrong.. Please try again!"); // Error message
        }

        return new ModelAndView("redirect:/admin/manage-posts"); // Redirect to manage-posts
    }

    @GetMapping("/admin/post/{id}")
    public ResponseEntity<BlogPost> getPostById(@PathVariable("id") Long id) {
        BlogPost blogPost = this.blogServices.getBlogPostById(id).get();
        return new ResponseEntity<BlogPost>(blogPost, HttpStatus.OK);
    }

    @GetMapping("/{url}")
    public String getBlogPost(@PathVariable("url") String url, Model model) {
        BlogPost blogPost = this.blogServices.getBlogPostBySlug(url).get();
        model.addAttribute("postInfo", blogPost);
        return "blog";
    }

    @GetMapping("/admin/post/visible/{id}")
    public String updatePost(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            this.blogServices.toggleVisibility(id);
            redirectAttributes.addFlashAttribute("success", "Visibility Updated..");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Something went wrong.. Please try again!");
        }
        return "redirect:/admin/manage-posts";
    }

    @GetMapping("/admin/post/delete/{id}")
    public String deletePost(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        try {
            this.blogServices.deleteBlogPost(id);
            redirectAttributes.addFlashAttribute("success", "Post deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete post!");
        }
        return "redirect:/admin/manage-posts"; // Redirect to the manage posts page
    }

}
