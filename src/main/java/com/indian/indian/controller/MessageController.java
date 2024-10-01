package com.indian.indian.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.indian.indian.entity.ContactUsMessage;
import com.indian.indian.service.MessageService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/admin/messages")
    public ModelAndView messages() {
        List<ContactUsMessage> messageList = this.messageService.getAllContactUsMessages();
        ModelAndView mv = new ModelAndView("/master-admin/manage-messages");
        mv.addObject("messageList", messageList);
        return mv;
    }

    @PostMapping("/message")
    public String sendMessage(@ModelAttribute ContactUsMessage message, RedirectAttributes redirectAttributes) {
        // Save the message
        this.messageService.saveContactUsMessage(message);
        redirectAttributes.addFlashAttribute("alert", "Message sent successfully!");
        // Redirect to the desired path
        return "redirect:/contact-us";
    }

    @GetMapping("/admin/message/delete/{id}")
    public ModelAndView deleteMessage(@PathVariable("id") Long id) {
        this.messageService.deleteContactUsMessage(id);
        return new ModelAndView(new RedirectView("/admin/messages"));
    }

}
