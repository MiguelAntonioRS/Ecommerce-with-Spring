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

        if (userDtls == null) {
            super.setDefaultFailureUrl("/signin?error=Invalid email or password");
            super.onAuthenticationFailure(request, response, exception);
            return;
        }

        // Verifica si la cuenta está habilitada
        if (!userDtls.getIsEnabled()) {
            exception = new LockedException("Account inactive");
        }
        // Verifica si la cuenta está bloqueada
        else if ((userDtls.getAccountNonBlocked() != null) && !userDtls.getAccountNonBlocked()) {
            if (userService.unlockAccountTimeExpired(userDtls)) {
                exception = new LockedException("Account is unlocked !! Please try to login");
            } else {
                exception = new LockedException("Account locked !! Please try after sometimes");
            }
        }
        // Cuenta activa y desbloqueada: maneja intentos fallidos
        else {
            int failedAttempts = (userDtls.getFailedAttempt() != null) ? userDtls.getFailedAttempt() : 0;

            if (failedAttempts < AppConstant.ATTEMPT_TIME) {
                userService.increaseFailedAttempt(userDtls);
            } else {
                userService.userAccountLock(userDtls);
                exception = new LockedException("Account is locked !! failed attempt " + (AppConstant.ATTEMPT_TIME + 1));
            }
        }

        super.setDefaultFailureUrl("/signin?error");
        super.onAuthenticationFailure(request, response, exception);
    }
}
