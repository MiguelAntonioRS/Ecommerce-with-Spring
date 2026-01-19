package com.ecom.Ecommerce_SpringBoot.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;

public class CommonUtil {

    @Autowired
    private static JavaMailSender mailSender;

    public static Boolean sendMail(String url, String recipientEmail) throws UnsupportedEncodingException, MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setFrom("rojassucarinomiguelantoni@gmail.com", "E-commerce");

        return false;
    }

    public static String generateUrl(HttpServletRequest request) {

        // http://localhost:8080/forgot-password
        String siteUrl = request.getRequestURL().toString();

        return siteUrl.replace(request.getServletPath(), "");
    }
}
