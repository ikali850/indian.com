package com.indian.indian.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
        List<Category> categoryList = this.categoryService.getAllCategories();
        ModelAndView mv = new ModelAndView("master-admin/manage-post", HttpStatus.OK);
        mv.addObject("blogList", blogList);
        mv.addObject("categoryList", categoryList);
        return mv;
    }

    @PostMapping("/admin/blog/post")
    public ModelAndView postMethodName(@ModelAttribute BlogPostRequestDTO blogPostDto, HttpSession session) {
        // get current user
        User currentUser = (User) session.getAttribute("user");
        System.out.println(currentUser);
        if (currentUser == null) {
            return new ModelAndView(new RedirectView("/signin"));
        }
        // create blogPost Entity Object
        BlogPost blogPost = new BlogPost();
        blogPost.setUser(currentUser);
        blogPost.setCreatedAt(blogPostDto.getDate());
        blogPost.setCategories(blogPostDto.getCategories());
        blogPost.setTitle(blogPostDto.getTitle());
        blogPost.setFullTitle(blogPostDto.getFullTitle());
        blogPost.setContent(blogPostDto.getContent());
        blogPost.setSlug(blogPostDto.getSlug());
        try {
            this.blogServices.saveBlogPost(blogPost);
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView(new RedirectView("/admin/manage-posts"));
            mv.addObject("error", "Something went Wrong.. Please Try Again!");
            return mv;
        }
        ModelAndView mv = new ModelAndView(new RedirectView("/admin/manage-posts"));
        mv.addObject("success", "Post Created..");
        return mv;
    }

    @GetMapping("/admin/post/verify")
    public ResponseEntity getMethodName(@RequestBody String url) {
        boolean exist = this.blogServices.existbySlug(url);
        if (exist) {
            return new ResponseEntity<String>("exist", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

    @PostMapping("/admin/edit-post")
    public ModelAndView updatePost(@ModelAttribute BlogPostRequestDTO blogPostDto, HttpSession session) {
        User user = (User) session.getAttribute("user");
        // create blogPost Entity Object
        BlogPost blogPost = new BlogPost();
        blogPost.setUser(user);
        blogPost.setId(blogPostDto.getId());
        blogPost.setCreatedAt(blogPostDto.getDate());
        blogPost.setCategories(blogPostDto.getCategories());
        blogPost.setTitle(blogPostDto.getTitle());
        blogPost.setFullTitle(blogPostDto.getFullTitle());
        blogPost.setContent(blogPostDto.getContent());
        blogPost.setSlug(blogPostDto.getSlug());
        try {
            this.blogServices.saveBlogPost(blogPost);
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView(new RedirectView("/admin/manage-posts"));
            mv.addObject("error", "Something went Wrong.. Please Try Again!");
            return mv;
        }
        ModelAndView mv = new ModelAndView(new RedirectView("/admin/manage-posts"));
        mv.addObject("success", "Post Updated..");
        return mv;
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<BlogPost> getPostById(@PathVariable("id") Long id) {
        BlogPost blogPost = this.blogServices.getBlogPostById(id).get();
        return new ResponseEntity<BlogPost>(blogPost, HttpStatus.OK);
    }

    @GetMapping("/test/{id}")
    public ResponseEntity<BlogPost> test(@PathVariable("id") Long id) {
        // System.out.println("test called");
        BlogPost blogPost = this.blogServices.getBlogPostById(id).get();
        return new ResponseEntity<BlogPost>(blogPost, HttpStatus.OK);
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
