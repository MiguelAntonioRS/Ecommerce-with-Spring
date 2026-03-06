package com.ecom.Ecommerce_SpringBoot.service;

import com.ecom.Ecommerce_SpringBoot.entities.UserDtls;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDtls saveUser(UserDtls user);

    UserDtls getUserByEmail(String email);

    List<UserDtls> getAllUsers(String role);

    Boolean updateAccountStatus(Integer id, Boolean status);

    void increaseFailedAttempt(UserDtls user);

    void userAccountLock(UserDtls user);

    Boolean unlockAccountTimeExpired(UserDtls user);

    void resetAttempt(int userId);

    void updateUserResetToken(String email, String resetToken);

    UserDtls getUserByToken(String token);

    UserDtls updateUser(UserDtls user);
}
