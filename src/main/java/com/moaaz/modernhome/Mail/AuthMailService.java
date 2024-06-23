package com.moaaz.modernhome.Mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class AuthMailService {


    @Autowired
    private JavaMailSender javaMailSender;

    public void sendPasswordToEmail(String email, String password) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("modernhomeinegypt@gmail.com");
            message.setTo(email);
            message.setText("Your Password From Modern Home Web Application System is "
                    + password + " \n I Will Kill You If You Forget Password Again...");
            message.setSubject("Modern Home");
            javaMailSender.send(message);

        } catch (MailException mailException) {
            throw mailException;
        }

    }

    public void sayHello(String email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("modernhomeinegypt@gmail.com");
            message.setTo(email);
            message.setText("Welcome In Our Online E-commerce , We Hope You Are Doing Well");
            message.setSubject("Modern Home");
            javaMailSender.send(message);
        } catch (MailException mailException) {
            throw mailException;
        }

    }

    public void sendMessageToAdmin(String content){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("modernhomeinegypt@gmail.com");
//            message.setTo("0114070264wetyuifjyr0@gmail.com");
            message.setTo("moaazsuliman1@gmail.com");
            message.setText(content);
            message.setSubject("Modern Home");
            javaMailSender.send(message);
        } catch (MailException mailException) {
            throw mailException;
        }
    }

}
