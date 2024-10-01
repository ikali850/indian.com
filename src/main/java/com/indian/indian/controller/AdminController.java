package com.indian.indian.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.indian.indian.entity.User;
import com.indian.indian.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public ModelAndView dashboardPage(HttpSession session) {
        ModelAndView mv = new ModelAndView("/master-admin/index", HttpStatus.OK);
        User user = (User) session.getAttribute("isAdmin");
        mv.addObject("user", user);
        return mv;
    }

    @GetMapping("/manage-users")
    public ModelAndView manageUsers() {
        List<User> userList = this.userService.getAllUsers();
        ModelAndView mv = new ModelAndView("/master-admin/manage-users", HttpStatus.OK);
        mv.addObject("users", userList);
        return mv;
    }

    @GetMapping("/profile/{id}")
    public ModelAndView userProfile(@PathVariable("id") long id) {
        User user = this.userService.getUserById(id).get();
        ModelAndView mv = new ModelAndView("/master-admin/user-profile", HttpStatus.OK);
        mv.addObject("user", user);
        return mv;
    }

}
