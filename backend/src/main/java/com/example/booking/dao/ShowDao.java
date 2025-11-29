package com.example.booking.dao;

import com.example.booking.entities.Show;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Stateless
public class ShowDao {
    @PersistenceContext(unitName = "ticketPU")
    private EntityManager em;

    public Optional<Show> findById(Long id) {
        try {
            Show s = em.createQuery("SELECT s FROM Show s LEFT JOIN FETCH s.movie LEFT JOIN FETCH s.screen WHERE s.id = :id", Show.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.of(s);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public List<Show> findAll() {
        return em.createQuery("SELECT s FROM Show s LEFT JOIN FETCH s.movie LEFT JOIN FETCH s.screen ORDER BY s.startTime", Show.class)
                .getResultList();
    }
}
