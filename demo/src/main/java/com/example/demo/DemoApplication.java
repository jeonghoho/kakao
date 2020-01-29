package com.example.demo;

import com.example.demo.service.CalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication implements CommandLineRunner {
	//@Autowired
	CalService calService;

//	DemoApplication(CalService calService){
//		this.calService = calService;
//	}

	@Autowired
	public void setCalService(CalService calService) {
		this.calService = calService;
	}

	@RequestMapping("/hello")
	public String hello(){
		return "hello~~~~~!!";
	}
	@GetMapping("/myname")
	public String myname(){
		return "kang kyung mi!!!";
	}
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("hello~~  spring boot!!");
		int resultValue = calService.plus(10,20);
		System.out.println(resultValue);
	}
}
