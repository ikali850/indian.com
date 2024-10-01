package com.indian.indian.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.indian.indian.Utils.PasswordUtils;
import com.indian.indian.entity.User;
import com.indian.indian.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminAuth {

    @Autowired
    private UserService userService;

    @PostMapping("/validate-admin")
    public String authenticateUser(
            @RequestParam("email") String email,
            @RequestParam("pass") String pass,
            RedirectAttributes redirectAttributes, HttpSession session) {
        // fetch user from db using email id
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()) {
            User currentUser = user.get();
            String userPasswd = currentUser.getPassword();
            if (currentUser.getIsBlocked().equals("unblock")) {
                // if user is block redirect user to signin screen with error message
                redirectAttributes.addFlashAttribute("alertMessage",
                        "Your Account has been Blocked Contact Master Admin!");
                return "redirect:/signin";
            }
            boolean isPasswdMatch = PasswordUtils.verifyPassword(pass, userPasswd);
            if (isPasswdMatch) {
                session.setAttribute("isAdmin", currentUser);
                session.setAttribute("user", currentUser);
                return "redirect:/admin/dashboard";
            }
        }
        redirectAttributes.addFlashAttribute("alertMessage", "Email or Password is incorrect!");
        return "redirect:/signin";
    }

    @PostMapping("/master-admin/validate")
    public String authenticateMasterAdmin(
            @RequestParam("email") String email,
            @RequestParam("pass") String pass,
            RedirectAttributes redirectAttributes, HttpSession session) {
        if (email.equals("kali@kali.com")) {
            String hashedpw = "$2a$10$cAsMr3zEIPyY/HPjaJAjb.KSqPeoNSjk0T2LXecLjDTkMs4by8RQS";
            boolean isPasswdMatch = PasswordUtils.verifyPassword(pass, hashedpw);
            if (isPasswdMatch) {
                Long id = (long) 1;
                User user = this.userService.getUserById(id).get();
                session.setAttribute("isAdmin", user);
                session.setAttribute("isMasterAdmin", user);
                session.setAttribute("user", user);
                return "redirect:/admin/dashboard";
            }
        }
        // Add flash attribute for error message
        redirectAttributes.addFlashAttribute("alertMessage", "Email or Password is incorrect!");
        return "redirect:/signin";
    }

    @GetMapping("/logout")
    public ModelAndView logOut(HttpSession session) {
        session.invalidate();
        return new ModelAndView(new RedirectView("/signin"));
    }

    @GetMapping("rest/password")
    public String resetPassword(@RequestParam("name") String name, @RequestParam("email") String email) {
        // logic to validate user and sent reset link to his email address
        return "reset link sent to your :" + email;
    }

}
