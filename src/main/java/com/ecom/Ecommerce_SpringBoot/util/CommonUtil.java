package com.ecom.Ecommerce_SpringBoot.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

public class CommonUtil {

    @Autowired
    private JavaMailSender mailSender;

    public static Boolean sendMail(String url, String recipientEmail) {

        return false;
    }

    public static String generateUrl(HttpServletRequest request) {

        // http://localhost:8080/forgot-password
        String siteUrl = request.getRequestURL().toString();

        return siteUrl.replace(request.getServletPath(), "");
    }
}
