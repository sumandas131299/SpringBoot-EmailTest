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
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;


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



    public void uploadEmail(MultipartFile file) throws IOException {

        InputStream is = file.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
        String line ="";
        List<email> list=new ArrayList();
        while((line=br.readLine())!=null){
            String[] s= line.split(",");
            email Email = new email();
            Email.setTo(s[0]);
            Email.setSubject(s[1]);
            Email.setMessage(s[2]);
           // System.out.println(s[0]);
            list.add(Email);
        }

        int index = 1;
        while(index<list.size()){
            System.out.println(list.get(index).getSubject());
            System.out.println(list.size());

            try{
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper actualMail = new MimeMessageHelper(message);

                // SimpleMailMessage actualMail = new SimpleMailMessage();
                actualMail.setFrom(fromEmail);
                actualMail.setTo(list.get(index).getTo());
                actualMail.setSubject(list.get(index).getSubject());
                actualMail.setText(list.get(index).getMessage());
                javaMailSender.send( message);


            }catch (Exception e){
                System.out.println("error in UploadOnlyEmail sending :: ServiceImp :: " + e );
                e.printStackTrace();
            }
                index++;
        }

    }
}
