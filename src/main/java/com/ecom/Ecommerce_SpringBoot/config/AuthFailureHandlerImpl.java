package com.ecom.Ecommerce_SpringBoot.config;

import com.ecom.Ecommerce_SpringBoot.entities.UserDtls;
import com.ecom.Ecommerce_SpringBoot.repository.UserRepository;
import com.ecom.Ecommerce_SpringBoot.service.UserService;
import com.ecom.Ecommerce_SpringBoot.util.AppConstant;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String email = request.getParameter("username");
        UserDtls userDtls = userRepository.findByEmail(email);

        if (userDtls.getIsEnabled()) {

            if (userDtls.getAccountNonBlocked()) {

                if (userDtls.getFailedAttempt()<= AppConstant.ATTEMPT_TIME) {
                    userService.increaseFailedAttempt(userDtls);
                } else {
                    userService.userAccountLock(userDtls);
                    exception = new LockedException("Account is locked !! failed attempt 3");
                }

            } else {
                exception = new LockedException("Account locked");
            }

        }else {
            exception = new LockedException("Account inactive");
        }

        super.onAuthenticationFailure(request, response, exception);
    }
}
