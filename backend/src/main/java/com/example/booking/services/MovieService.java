package com.example.booking.services;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import com.example.booking.dao.MovieDao;
import com.example.booking.entities.Movie;

import java.util.List;
import java.util.Optional;

@Stateless
public class MovieService {
    @Inject
    private MovieDao movieDao;

    public Optional<Movie> findById(Long id) {
        return movieDao.findById(id);
    }

    public List<Movie> findAll() {
        return movieDao.findAll();
    }

    public Movie create(Movie m) {
        return movieDao.create(m);
    }
}
