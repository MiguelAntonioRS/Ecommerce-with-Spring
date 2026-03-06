package com.ecom.Ecommerce_SpringBoot.util;

import com.ecom.Ecommerce_SpringBoot.entities.ProductOrder;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@RequiredArgsConstructor
public class CommonUtil {

    private final JavaMailSender mailSender;

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

    public Boolean sendOrderMail(ProductOrder productOrder, String codeStatus) throws UnsupportedEncodingException, MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom("rojassucarinomiguelantoni@gmail.com", "E-commerce");
        helper.setTo(productOrder.getAddressOrder().getEmail());

        String content = "<p>Hello [[name]]</p>,<p>Thanks order successfully <b>[[orderStatus]]</b>.</p>" +
                "<p><b>Product Details</b> :</p>"
                + "<p>Name : [[productName]]</p>"
                + "<p>Category : [[category]]</p>"
                + "<p>Quantity : [[quantity]]</p>"
                + "<p>Price : [[price]]</p>"
                + "<p>Payment Type : [[paymentType]]</p>";

        content = content.replace("[[name]]", productOrder.getAddressOrder().getFirstName());
        content = content.replace("[[orderStatus]]", codeStatus);
        content = content.replace("[[productName]]", productOrder.getProduct().getTitle());
        content = content.replace("[[category]]", productOrder.getProduct().getCategory());
        content = content.replace("[[quantity]]", String.valueOf(productOrder.getQuantity()));
        content = content.replace("[[price]]", String.valueOf(productOrder.getPrice()));
        content = content.replace("[[paymentType]]", productOrder.getPaymentType());

        helper.setSubject("Order Product Status");
        helper.setText(content, true);
        mailSender.send(mimeMessage);

        return true;
    }
}
