package com.example.booking.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;
import com.example.booking.entities.Movie;
import java.util.List;
import java.util.Optional;

@Stateless
public class MovieDao {
    @PersistenceContext(unitName = "ticketPU")
    private EntityManager em;

    public Optional<Movie> findById(Long id) {
        try {
            Movie m = em.find(Movie.class, id);
            return Optional.ofNullable(m);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public List<Movie> findAll() {
        return em.createQuery("SELECT m FROM Movie m ORDER BY m.title", Movie.class).getResultList();
    }

    public Movie create(Movie m) {
        em.persist(m);
        return m;
    }
}
