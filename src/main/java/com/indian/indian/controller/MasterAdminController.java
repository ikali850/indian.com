package com.indian.indian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    public ModelAndView createUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        String result = this.userService.registerUser(user);
        ModelAndView mv = new ModelAndView();

        if (result.equals("created")) {
            redirectAttributes.addFlashAttribute("success", "Account created...");
        } else if (result.equals("emailExist")) {
            redirectAttributes.addFlashAttribute("error", "Email Already Exist!");
        } else if (result.equals("mobileExist")) {
            redirectAttributes.addFlashAttribute("error", "Mobile number Already Exist!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Something went wrong!");
        }

        mv.setViewName("redirect:/admin/manage-users"); // Redirect to the manage-users page
        return mv;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ModelAndView(new RedirectView("/admin/manage-users"));
    }

    @GetMapping("/block/{id}")
    public ModelAndView blockUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        User user = userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setIsBlocked("unblock");
        userService.updateUser(user);
        redirectAttributes.addFlashAttribute("success", "User blocked successfully.");
        return new ModelAndView("redirect:/admin/manage-users");
    }

    @GetMapping("/unblock/{id}")
    public ModelAndView unBlockUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        User user = userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setIsBlocked("block");
        userService.updateUser(user);
        redirectAttributes.addFlashAttribute("success", "User unblocked successfully."); // Add success message
        return new ModelAndView("redirect:/admin/manage-users"); // Redirect to manage-users
    }

}
