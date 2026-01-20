package com.ecom.Ecommerce_SpringBoot.service;

import com.ecom.Ecommerce_SpringBoot.entities.UserDtls;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public UserDtls saveUser(UserDtls user);

    public UserDtls getUserByEmail(String email);

    public List<UserDtls> getAllUsers(String role);

    public Boolean updateAccountStatus(Integer id, Boolean status);

    public void increaseFailedAttempt(UserDtls user);

    public void userAccountLock(UserDtls user);

    public Boolean unlockAccountTimeExpired(UserDtls user);

    public void resetAttempt(int userId);

    public void updateUserResetToken(String email, String resetToken);

    public UserDtls getUserByToken(String token);
}
