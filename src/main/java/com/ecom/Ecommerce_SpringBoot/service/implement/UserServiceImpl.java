package com.ecom.Ecommerce_SpringBoot.service.implement;

import com.ecom.Ecommerce_SpringBoot.entities.UserDtls;
import com.ecom.Ecommerce_SpringBoot.persistence.UserDAO;
import com.ecom.Ecommerce_SpringBoot.repository.UserRepository;
import com.ecom.Ecommerce_SpringBoot.service.UserService;
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
        return null;
    }

    @Override
    public void resetAttempt(int userId) {

    }
}
