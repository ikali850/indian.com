package com.indian.indian.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.indian.indian.entity.ContactUsMessage;

@Repository
public interface MessagesRepository extends JpaRepository<ContactUsMessage, Long> {

    List<ContactUsMessage> findByEmail(String email);

}
