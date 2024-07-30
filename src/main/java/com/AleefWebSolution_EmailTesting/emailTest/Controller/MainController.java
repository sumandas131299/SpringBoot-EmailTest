package com.AleefWebSolution_EmailTesting.emailTest.Controller;

import com.AleefWebSolution_EmailTesting.emailTest.Model.email;
import com.AleefWebSolution_EmailTesting.emailTest.Service.ServiceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class MainController {

@Autowired
ServiceImp service;


    @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody email Email){
         this.service.sendEmail(Email);
         return  "Mail Send Successfully";
    }

    @PostMapping("/uploadCsvMime")
    public String uploadEmail(@RequestParam("file") MultipartFile file) throws IOException {
        this.service.uploadEmail(file);
        return "Mails successfully";
    }

    @PostMapping("/uploadCsvWithoutBodyMime")
    public String uploadWithoutBody(@RequestParam("csv")MultipartFile file ,@RequestParam("sub")String sub,@RequestParam("body")String body ) throws IOException {
    this.service.uploadWithoutBody(file , sub,body);
    return "Mail sent successfully";
    }

    @PostMapping("/uploadCsvSmtp")
    public String uploadWithSmtp(@RequestParam("file") MultipartFile file) throws IOException {
        this.service.uploadWithSmtp(file);
        return "Mail sent successfully";

    }

    @PostMapping("/uploadCsvWithoutBodySmtp")
    public String uploadWithoutBodySmtp(@RequestParam("csv")MultipartFile file , @RequestParam("sub")String sub,@RequestParam("body")String body) throws IOException{
        this.service.uploadWithoutBodySmtp(file,sub,body);
        return "Mail sent successful";
    }

    @PostMapping("/uploadCsvWithImage")
    public String uploadCsvWithImage(@RequestParam("csv") MultipartFile file , @RequestParam("img") MultipartFile image) throws IOException {
        this.service.uploadCsvWithImage(file,image);
        return "Mail sent with attachment";

    }

    @PostMapping("/convertHtmlToPdf")
    public ResponseEntity<byte[]>   convertHtmlToPdf(@RequestBody String html){
        try{
            byte[] pdf = this.service.convertHtmlToPdf(html);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"document.pdf\"").contentType(MediaType.APPLICATION_PDF).body(pdf);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        }

    @PostMapping("/uploadHtmlToPdfAttachment")
    public ResponseEntity htmlToPdfAttachment(@RequestParam ("csv") MultipartFile csv , @RequestParam ("html")MultipartFile html) throws IOException {
        this.service.htmlToPdfAttachment(csv,html);
        return ResponseEntity.ok().body("mail sent with pdf attachment");
    }


}
