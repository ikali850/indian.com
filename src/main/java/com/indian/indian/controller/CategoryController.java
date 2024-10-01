package com.indian.indian.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.indian.indian.entity.Category;
import com.indian.indian.service.CategoryService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin/manage-categories")
    public ModelAndView manageCategory() {
        List<Category> list = categoryService.getAllCategories();
        ModelAndView mv = new ModelAndView("/master-admin/manage-category", HttpStatus.OK);
        mv.addObject("list", list);
        return mv;
    }

    @PostMapping("/admin/category")
    public ModelAndView addCategory(@ModelAttribute Category category, RedirectAttributes redirectAttributes) {
        // check if already exist by name
        boolean isExist = this.categoryService.categoryExist(category.getName());
        if (isExist) {
            // Add a flash attribute for the alert message
            redirectAttributes.addFlashAttribute("error", "Category Exist!");
            ModelAndView mv = new ModelAndView();
            mv.setView(new RedirectView("/admin/manage-categories"));
            return mv;
        }
        try {
            this.categoryService.createCategory(category);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Something went Wrong !");
            ModelAndView mv = new ModelAndView();
            mv.setView(new RedirectView("/admin/manage-categories"));
            return mv;
        }
        redirectAttributes.addFlashAttribute("alert", "Category added successfully!");
        ModelAndView mv = new ModelAndView();
        mv.setView(new RedirectView("/admin/manage-categories"));
        return mv;
    }

    @PostMapping("/admin/category/update")
    public ModelAndView updateCategory(@ModelAttribute Category category, RedirectAttributes redirectAttributes) {
        // check if already exist by name
        boolean isExist = this.categoryService.categoryExist(category.getId());
        if (!isExist) {
            // Add a flash attribute for the alert message
            redirectAttributes.addFlashAttribute("error", "Category Does not Exist!");
            ModelAndView mv = new ModelAndView();
            mv.setView(new RedirectView("/admin/manage-categories"));
            return mv;
        }
        try {
            this.categoryService.createCategory(category);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Something went Wrong !");
            ModelAndView mv = new ModelAndView();
            mv.setView(new RedirectView("/admin/manage-categories"));
            return mv;
        }
        redirectAttributes.addFlashAttribute("alert", "Category updated successfully!");
        ModelAndView mv = new ModelAndView();
        mv.setView(new RedirectView("/admin/manage-categories"));
        return mv;
    }

    @GetMapping("/admin/category/delete/{id}")
    public ModelAndView deleteCategory(@PathVariable("id") long id) {
        this.categoryService.deleteCategory(id);
        return new ModelAndView(new RedirectView("/admin/manage-categories"));

    }

}
