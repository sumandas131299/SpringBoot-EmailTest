package com.AleefWebSolution_EmailTesting.emailTest.Controller;

import com.AleefWebSolution_EmailTesting.emailTest.Model.email;
import com.AleefWebSolution_EmailTesting.emailTest.Service.ServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

@Autowired
ServiceImp service;


    @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody email Email){
         this.service.sendEmail(Email);
         return  "Mail Send Sucessfully";
    }




}
