package com.moaaz.modernhome.Mail;

import com.moaaz.modernhome.Order.Order;
import jakarta.mail.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OrderMailService {

    @Autowired
    private JavaMailSender javaMailSender;


    public void notifyUser(Order order) {
        sendMessage(order.getUser().getEmail(), "Your Order With Code '" + order.getCode() + "' Has Been Created");
    }

    public void notifyUserOrderIsAccepted(Order order) {
        sendMessage(order.getUser().getEmail(), "Your Order With Code '" + order.getCode() + "' Has Been Accepted , You Will Get It in 2 days");
    }

    public void notifyUserOrderIsRejected(Order order) {
        sendMessage(order.getUser().getEmail(), "Your Order With Code '" + order.getCode() + "' Has Been Rejected");
    }

    public void sendMessage(String email, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("modernhomeinegypt@gmail.com");
            message.setTo(email);
            message.setText(content);
            message.setSubject("Modern Home");
            javaMailSender.send(message);

        } catch (MailException mailException) {
            throw mailException;
        }
    }

}
