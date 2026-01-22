package com.ecom.Ecommerce_SpringBoot.service.implement;

import com.ecom.Ecommerce_SpringBoot.entities.UserDtls;
import com.ecom.Ecommerce_SpringBoot.persistence.UserDAO;
import com.ecom.Ecommerce_SpringBoot.repository.UserRepository;
import com.ecom.Ecommerce_SpringBoot.service.UserService;
import com.ecom.Ecommerce_SpringBoot.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDtls saveUser(UserDtls user) {
        return userDAO.saveUser(user);
    }

    @Override
    public UserDtls getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    @Override
    public List<UserDtls> getAllUsers(String role) {
        return userDAO.getAllUsers(role);
    }

    @Override
    public Boolean updateAccountStatus(Integer id, Boolean status) {
        return userDAO.updateAccountStatus(id, status);
    }

    @Override
    public void increaseFailedAttempt(UserDtls user) {

        int attempt = user.getFailedAttempt() + 1;
        user.setFailedAttempt(attempt);
        userRepository.save(user);
    }

    @Override
    public void userAccountLock(UserDtls user) {

        user.setAccountNonBlocked(false);
        user.setLockTime(new Date());
        userRepository.save(user);
    }

    @Override
    public Boolean unlockAccountTimeExpired(UserDtls user) {

        Long lockTime = user.getLockTime().getTime();
        Long unLockTime = lockTime + AppConstant.UNLOCK_DURATION_TIME;
        long currentTime = System.currentTimeMillis();

        if (unLockTime<currentTime) {

            user.setAccountNonBlocked(true);
            user.setFailedAttempt(0);
            user.setLockTime(null);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public void resetAttempt(int userId) {

    }

    @Override
    public void updateUserResetToken(String email, String resetToken) {
        UserDtls findByEmail = userRepository.findByEmail(email);
        findByEmail.setResetToken(resetToken);
        userRepository.save(findByEmail);
    }

    @Override
    public UserDtls getUserByToken(String token) {

        return userRepository.findByResetToken(token);
    }

    @Override
    public UserDtls updateUser(UserDtls user) {
        return userRepository.save(user);
    }
}
