package com.indian.indian.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indian.indian.entity.ContactUsMessage;
import com.indian.indian.repository.MessagesRepository;

@Service
public class MessageService {

    @Autowired
    private MessagesRepository MessagesRepository;

    // Save a ContactUsMessage
    public ContactUsMessage saveContactUsMessage(ContactUsMessage ContactUsMessage) {
        return MessagesRepository.save(ContactUsMessage);
    }

    // Delete a ContactUsMessage by ID
    public void deleteContactUsMessage(Long id) {
        MessagesRepository.deleteById(id);
    }

    // Get all ContactUsMessages
    public List<ContactUsMessage> getAllContactUsMessages() {
        return MessagesRepository.findAll();
    }

    // Find a ContactUsMessage by ID
    public Optional<ContactUsMessage> findContactUsMessageById(Long id) {
        return MessagesRepository.findById(id);
    }

    // Find ContactUsMessages by email
    public List<ContactUsMessage> findContactUsMessagesByEmail(String email) {
        return MessagesRepository.findByEmail(email);
    }
}
