package com.AleefWebSolution_EmailTesting.emailTest.Service;

import com.AleefWebSolution_EmailTesting.emailTest.Model.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import  org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.sql.DataSource;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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

            @NotNull(message = "email is required")
            @Email(message = "give a valid email address")
            String to = Email.getTo();

            actualMail.setTo(to);
            actualMail.setSubject(Email.getSubject());
            actualMail.setText(Email.getMessage());
            javaMailSender.send( message);


        }catch (Exception e){
            System.out.println("error in sendEmail :: ServiceImp :: " + e );
            e.printStackTrace();
        }



    }

//    This method contain subject and body into the csv file and in MIME

    public void uploadEmail(MultipartFile file) throws IOException {

        InputStream is = file.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
        String line ="";
        List<email> list=new ArrayList();
        while((line=br.readLine())!=null){
            String[] s= line.split(",");
            email Email = new email();

            @NotNull(message = "email is required")
            @Email(message = "give a valid email address")
            String to = s[0];

            Email.setTo(to);
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

    //    This method doesn't contain subject and body into the csv file and in MIME

    public void uploadWithoutBody(MultipartFile file, String sub ,String body) throws IOException {

        InputStream is = file.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
        String line ="";
        List<email> emailList=new ArrayList();
        while((line=br.readLine())!=null){
            String[] s= line.split(",");
            email Email = new email();

            @NotNull(message = "email is required")
            @Email(message = "give a valid email address")
            String to = s[0];

            Email.setTo(to);


            Email.setSubject((String) sub);
            Email.setMessage((String) body);
            // System.out.println(s[0]);
            emailList.add(Email);
        }

        int index = 1;
        while(index<emailList.size()){
            System.out.println(emailList.get(index).getSubject());
            System.out.println(emailList.size());

            try{
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper actualMail = new MimeMessageHelper(message);

                // SimpleMailMessage actualMail = new SimpleMailMessage();
                actualMail.setFrom(fromEmail);
                actualMail.setTo(emailList.get(index).getTo());
                actualMail.setSubject(emailList.get(index).getSubject());
                actualMail.setText(emailList.get(index).getMessage());
                javaMailSender.send( message);


            }catch (Exception e){
                System.out.println("error in uploadWithoutBody  :: ServiceImp :: " + e );
                e.printStackTrace();
            }
            index++;
        }


    }

  //  This method contain subject and body into the csv file and in SMTP
    public void uploadWithSmtp(MultipartFile file) throws IOException {
        InputStream is = file.getInputStream();
        BufferedReader br  = new BufferedReader(new InputStreamReader(is ,"UTF-8"));
        String line = "";
        List<email> list =new ArrayList();
        while((line= br.readLine())!=null){
            String[] s = line.split(",");
            email Email = new email();

            @NotNull(message = "email cannot be null")
            @Email(message = "provide proper email address")
             String to =s[0];

            Email.setTo(to);
            Email.setSubject(s[1]);
            Email.setMessage(s[2]);
            list.add(Email);

        }

        int index = 1;
        while(index<list.size()){
            System.out.println(list.get(index).getSubject());
            System.out.println(list.size());

            try{
                // MimeMessage message = javaMailSender.createMimeMessage();
                // MimeMessageHelper actualMail = new MimeMessageHelper(message);

                SimpleMailMessage actualMail = new SimpleMailMessage();
                actualMail.setFrom(fromEmail);
                actualMail.setTo(list.get(index).getTo());
                actualMail.setSubject(list.get(index).getSubject());
                actualMail.setText(list.get(index).getMessage());
                javaMailSender.send( actualMail);


            }catch (Exception e){
                System.out.println("error in UploadWithSmtp :: ServiceImp :: " + e );
                e.printStackTrace();
            }
            index++;
        }

    }

    //    This method doesn't contain subject and body into the csv file and in SMTP

    public void uploadWithoutBodySmtp(MultipartFile file, String sub, String body) throws IOException {
        InputStream is= file.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
        String line ="";
        List<email> emailList=new ArrayList();
        while((line=br.readLine())!=null){
            String[] s= line.split(",");
            email Email = new email();

            @NotNull(message = "email is required")
            @Email(message = "give a valid email address")
            String to = s[0];

            Email.setTo(to);


            Email.setSubject((String) sub);
            Email.setMessage((String) body);
            // System.out.println(s[0]);
            emailList.add(Email);
        }

        int index = 1;
        while(index<emailList.size()){
            System.out.println(emailList.get(index).getSubject());
            System.out.println(emailList.size());

            try{
                //   MimeMessage message = javaMailSender.createMimeMessage();
                //  MimeMessageHelper actualMail = new MimeMessageHelper(message);

                SimpleMailMessage actualMail = new SimpleMailMessage();
                actualMail.setFrom(fromEmail);
                actualMail.setTo(emailList.get(index).getTo());
                actualMail.setSubject(emailList.get(index).getSubject());
                actualMail.setText(emailList.get(index).getMessage());
                javaMailSender.send( actualMail);


            }catch (Exception e){
                System.out.println("error in uploadWithoutBodySmtp  :: ServiceImp :: " + e );
                e.printStackTrace();
            }
            index++;
        }
    }

    //    This method contain subject and body into the csv file and in MIME with Attachment file as Image

    public void uploadCsvWithImage(MultipartFile file, MultipartFile image) throws IOException {

            InputStream is = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            String line ="";
            List<email> list=new ArrayList();
            while((line=br.readLine())!=null){
                String[] s= line.split(",");
                email Email = new email();

                @NotNull(message = "email is required")
                @Email(message = "give a valid email address")
                String to = s[0];

                Email.setTo(to);
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
                    MimeMessageHelper actualMail = new MimeMessageHelper(message,true);

                    // SimpleMailMessage actualMail = new SimpleMailMessage();
                    actualMail.setFrom(fromEmail);
                    actualMail.setTo(list.get(index).getTo());
                    actualMail.setSubject(list.get(index).getSubject());
                    actualMail.setText(list.get(index).getMessage());
                   //if(image!=null && !image.isEmpty())
                       actualMail.addAttachment((Objects.requireNonNull(image.getOriginalFilename())), image);
                    javaMailSender.send( message);


                }catch (Exception e){
                    System.out.println("error in UploadCsvWithAttachment :: ServiceImp :: " + e );
                    e.printStackTrace();
                }
                index++;
            }
    }

    //    This method convert html to pdf and send it back to download;
    public byte[] convertHtmlToPdf(String html) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(os);
        return os.toByteArray();
    }

    //    This method convert html to pdf and send it with mail as attachment;

    public void htmlToPdfAttachment(MultipartFile csv,@NotNull MultipartFile html) throws IOException {

        //html multipartFile converted into string

        BufferedReader br = new BufferedReader(new InputStreamReader(html.getInputStream(),"UTF-8"));
        String line;
        StringBuilder sb = new StringBuilder();
        while((line=br.readLine())!=null){
            sb.append(line);
        }
        //string to pdf

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ITextRenderer render = new ITextRenderer();
        render.setDocumentFromString(sb.toString());
        render.layout();
        render.createPDF(os);
        byte[] pdf = os.toByteArray();

        InputStream is = csv.getInputStream();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(is ,"UTF-8"));
        String li;
        List<email> list = new ArrayList<>();
        while((li=buffer.readLine())!=null){
            String [] s = li.split(",");
            email Email = new email();

            @Email(message = "Enter valid message")
            @NotNull(message = "Email cannot be null")
            String to = s[0];

            Email.setTo(to);
            Email.setSubject(s[1]);
            Email.setMessage(s[2]);

            list.add(Email);

        }

        int index = 1;
        while(index<list.size()){
            System.out.println(list.get(index).getSubject());
            System.out.println(list.size());

            try{
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper actualMail = new MimeMessageHelper(message,true);

                // SimpleMailMessage actualMail = new SimpleMailMessage();
                actualMail.setFrom(fromEmail);
                actualMail.setTo(list.get(index).getTo());
                actualMail.setSubject(list.get(index).getSubject());
                actualMail.setText(list.get(index).getMessage());
                //if(image!=null && !image.isEmpty())

               // DataSource pdfData = (DataSource) new ByteArrayDataSource(pdf , "application/pdf");
                actualMail.addAttachment("document.pdf", new ByteArrayDataSource(pdf , "application/pdf"));
                javaMailSender.send( message);


            }catch (Exception e){
                System.out.println("error in UploadCsvWithAttachment :: ServiceImp :: " + e );
                e.printStackTrace();
            }
            index++;
        }

    }
}
