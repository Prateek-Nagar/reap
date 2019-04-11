package com.prateek.reap.service;

import com.prateek.reap.entity.User;
import com.prateek.reap.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    public Optional<User> validateUser(User responseData) {
        Optional<User> loginStatus = loginRepository
                .findByEmailAndPassword(responseData.getEmail(), responseData.getPassword());
        return loginStatus;
    }

    public Optional<User> find(int userId) {
        Optional<User> id = loginRepository.findById(userId);
        return id;
    }

    public User findUserByEmail(String email) {
        return loginRepository.findByEmail(email);
    }


}
