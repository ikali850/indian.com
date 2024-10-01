package com.indian.indian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.indian.indian.Utils.PasswordUtils;
import com.indian.indian.entity.User;
import com.indian.indian.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin/master-admin/user")
public class MasterAdminController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ModelAndView createUser(@ModelAttribute User user) {
        // check if user exist by email address
        if (this.userService.getUserByEmail(user.getEmail()).isPresent()) {
            ModelAndView mv = new ModelAndView("/master-admin/manage-users");
            mv.addObject("emailExist", true);
            return mv;
        }
        // check if user exist by mobile number
        if (this.userService.getUserByMobile(user.getMobile()).isPresent()) {
            ModelAndView mv = new ModelAndView("/master-admin/manage-users");
            mv.addObject("mobileExist", true);
            return mv;
        }
        String plainPass = user.getPassword();
        String hashedpw = PasswordUtils.generatePassword(plainPass);
        user.setPassword(hashedpw);
        try {
            User createdUser = userService.registerUser(user);
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView("/master-admin/manage-users");
            mv.addObject("serverError", true);
            return mv;
        }
        ModelAndView mv = new ModelAndView("/master-admin/manage-users");
        mv.addObject("isSuccess", true);
        return mv;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        String plainPass = userDetails.getPassword();
        String hashedpw = PasswordUtils.generatePassword(plainPass);
        userDetails.setPassword(hashedpw);
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ModelAndView(new RedirectView("/admin/manage-users"));
    }

    @GetMapping("/block/{id}")
    public ModelAndView blockUser(@PathVariable Long id) {
        User user = userService.getUserById(id).get();
        System.out.println(user);
        user.setIsBlocked("unblock");
        userService.registerUser(user);
        return new ModelAndView(new RedirectView("/admin/manage-users"));
    }

    @GetMapping("/unblock/{id}")
    public ModelAndView unBlockUser(@PathVariable Long id) {
        User user = userService.getUserById(id).get();
        System.out.println(user);
        user.setIsBlocked("block");
        userService.registerUser(user);
        return new ModelAndView(new RedirectView("/admin/manage-users"));
    }
}
