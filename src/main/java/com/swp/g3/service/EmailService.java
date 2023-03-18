package com.swp.g3.service;

import com.swp.g3.util.EmailHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private EmailHelper emailHelper;
    public void sendVerifyEmail(String toEmail, String username, String name) throws MessagingException{
            String from = "1duckyltt@gmail.com";

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setSubject("XÁC THỰC EMAIL");
            helper.setFrom(from);
            helper.setTo(toEmail);

            boolean html = true;
            String text = "<b>Xin chào " + name + "</b>,<br><i>Chào mừng đến với Bảo Hiểm Xe Máy</i><br>Vui lòng nhấn vào liên kết bên dưới để hoàn tất quá trình đăng ký: <br> <a href=''>"
                            + emailHelper.generateVerifyLink(username) + "</a>";
            helper.setText(text, html);
            mailSender.send(message);
    }
    public void sendResetPasswordEmail(String toEmail, String username, String name) throws MessagingException{
        String from = "1duckyltt@gmail.com";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setSubject("ĐẶT LẠI MẬT KHẨU");
        helper.setFrom(from);
        helper.setTo(toEmail);

        boolean html = true;
        String text = "<b>Xin chào " + name + "</b>,<br><i>Chào mừng đến với Bảo Hiểm Xe Máy</i><br>Vui lòng nhấn vào liên kết bên dưới để cài đặt lại mật khẩu: <br> <a href=''>"
                + emailHelper.generateResetPasswordLink(username) + "</a>";
        helper.setText(text, html);
        mailSender.send(message);
    }
}
