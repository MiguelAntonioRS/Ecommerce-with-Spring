package com.ecom.Ecommerce_SpringBoot.util;

import com.ecom.Ecommerce_SpringBoot.entities.ProductOrder;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;

@Component
public class CommonUtil {

    @Autowired
    private JavaMailSender mailSender;

    public Boolean sendMail(String url, String recipientEmail) throws UnsupportedEncodingException, MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom("rojassucarinomiguelantoni@gmail.com", "E-commerce");
        helper.setTo(recipientEmail);

        String content = "<p>Hello,</p>" + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>" + "<p><a href=\"" + url
                + "\">Change my password</a></p>";

        helper.setSubject("Password Reset");
        helper.setText(content, true);
        mailSender.send(mimeMessage);

        return true;
    }

    public static String generateUrl(HttpServletRequest request) {

        // http://localhost:8080/forgot-password
        String siteUrl = request.getRequestURL().toString();

        return siteUrl.replace(request.getServletPath(), "");
    }

    public Boolean sendOrderMail(ProductOrder productOrder, Integer codeStatus) throws UnsupportedEncodingException, MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom("rojassucarinomiguelantoni@gmail.com", "E-commerce");
        helper.setTo(productOrder.getAddressOrder().getEmail());

        StatusOrder[] values = StatusOrder.values();
        for (StatusOrder statusOrder:values) {

            if (statusOrder.getId().equals(codeStatus)) {

            }
        }

        String content = "<p>Thanks order successfully <b>[[orderStatus]]</b></p>" +
                "<p>Product Details :</p>"
                + "<p>Name : [[productName]]</p>"
                + "<p>Category : [[category]]</p>"
                + "<p>Quantity : [[quantity]]</p>"
                + "<p>Price : [[price]]</p>"
                + "<p>Payment Type : [[paymentType]]</p>";

        content = content.replace("[[productName]]", productOrder.getProduct().getTitle());
        content = content.replace("[[category]]", productOrder.getProduct().getCategory());
        content = content.replace("[[quantity]]", String.valueOf(productOrder.getQuantity()));
        content = content.replace("[[price]]", String.valueOf(productOrder.getPrice()));
        content = content.replace("[[paymentType]]", productOrder.getPaymentType());

        helper.setSubject("Order Product Status");
        helper.setText(content);
        mailSender.send(mimeMessage);

        return true;
    }
}
