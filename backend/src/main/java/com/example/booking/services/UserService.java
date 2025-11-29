package com.example.booking.services;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import com.example.booking.dao.UserDao;
import com.example.booking.entities.User;

import java.util.List;
import java.util.Optional;

@Stateless
public class UserService {
    @Inject
    private UserDao userDao;

    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public User create(User u) {
        return null;
    }

    public List<User> all() {
    }

    public User get(Long id) {
    }
}
