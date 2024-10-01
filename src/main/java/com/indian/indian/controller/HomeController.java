package com.indian.indian.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController {

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
