package com.AleefWebSolution_EmailTesting.emailTest.Controller;

import com.AleefWebSolution_EmailTesting.emailTest.Model.email;
import com.AleefWebSolution_EmailTesting.emailTest.Service.ServiceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class MainController {

@Autowired
ServiceImp service;


    @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody email Email){
         this.service.sendEmail(Email);
         return  "Mail Send Sucessfully";
    }

    @PostMapping("/uploadOnlyEmail")
    public String uploadEmail(@RequestParam("file") MultipartFile file) throws IOException {
        this.service.uploadEmail(file);
        return "Mails sucessfully";
    }



}
