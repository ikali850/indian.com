package com.indian.indian;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

import com.indian.indian.Utils.PasswordUtils;
import com.indian.indian.entity.BlogPost;
import com.indian.indian.repository.BlogRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
public class IndianApplication {

	public static void main(String[] args) {
		SpringApplication.run(IndianApplication.class, args);
		System.out.println("Application Started..");
	}

}
