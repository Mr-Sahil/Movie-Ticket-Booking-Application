package com.example.booking.services;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import com.example.booking.dao.ScreenDao;
import com.example.booking.entities.Screen;

import java.util.List;
import java.util.Optional;

@Stateless
public class ScreenService {
    @Inject
    private ScreenDao screenDao;

    public Optional<Screen> findById(Long id) {
        return screenDao.findById(id);
    }

    public List<Screen> findAll() {
        return screenDao.findAll();
    }

    public Screen create(Screen s) {
        return screenDao.create(s);
    }
}
