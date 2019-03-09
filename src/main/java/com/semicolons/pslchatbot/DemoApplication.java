package com.semicolons.pslchatbot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {
  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @GetMapping("/")
  public String hello() {
    return "hello world!";
  }
  
  @GetMapping("/getRevenueAccounts")
  public List<String> getRevenueAccounts() {
  	List<String> list = new ArrayList<>();
  	list.add("Thermofisher");
  	list.add("Mobitv");
  	list.add("Wells Fargo");
  	return list;
  }

}
