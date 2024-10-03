package com.indian.indian.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.indian.indian.entity.BlogPost;
import com.indian.indian.repository.BlogRepository;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HomeController {

  @Autowired
  private BlogRepository blogRepository;

  @GetMapping
  public ModelAndView homepage() {
    return new ModelAndView("index.html", HttpStatus.OK);
  }

  @GetMapping("/blog")
  public ModelAndView postPage() {
    return new ModelAndView("blog.html", HttpStatus.OK);
  }

  @GetMapping("/results")
  public ModelAndView resultPage() {
    ModelAndView mv = new ModelAndView("list");
    List<BlogPost> resultList = this.blogRepository.findByCategory("job");
    mv.addObject("listData", resultList);
    mv.addObject("title", "Results");
    mv.addObject("value2", "Recent Exams");
    return mv;
  }

  @GetMapping("/contact-us")
  public ModelAndView contactUsPage() {
    return new ModelAndView("contact-us.html", HttpStatus.OK);
  }

  @GetMapping("/about-us")
  public ModelAndView aboutUsPage() {
    return new ModelAndView("about-us.html", HttpStatus.OK);
  }

  @GetMapping("/not-found")
  public ModelAndView errorPage() {
    return new ModelAndView("error.html", HttpStatus.OK);
  }

  @GetMapping("/signin")
  public ModelAndView signInPage() {
    return new ModelAndView("admin-signin.html", HttpStatus.OK);
  }

}
