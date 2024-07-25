package com.AleefWebSolution_EmailTesting.emailTest.Service;

import com.AleefWebSolution_EmailTesting.emailTest.Model.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import  org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;


@Service
public class ServiceImp {

    @Autowired
    JavaMailSender javaMailSender;

    @Value("$(spring.mail.username)")
    private String fromEmail;

    public void sendEmail(email Email)  {
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper actualMail = new MimeMessageHelper(message);

           // SimpleMailMessage actualMail = new SimpleMailMessage();
            actualMail.setFrom(fromEmail);
            actualMail.setTo(Email.getTo());
            actualMail.setSubject(Email.getSubject());
            actualMail.setText(Email.getMessage());
            javaMailSender.send( message);


        }catch (Exception e){
            System.out.println("error in sendEmail :: ServiceImp :: " + e );
            e.printStackTrace();
        }




    }


}
