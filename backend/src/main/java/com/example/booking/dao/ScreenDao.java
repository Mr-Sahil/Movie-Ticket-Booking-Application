package com.example.booking.dao;

import com.example.booking.entities.Screen;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Stateless
public class ScreenDao {
    @PersistenceContext(unitName = "ticketPU")
    private EntityManager em;

    public Optional<Screen> findById(Long id) {
        try {
            Screen s = em.find(Screen.class, id);
            return Optional.ofNullable(s);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public List<Screen> findAll() {
        return em.createQuery("SELECT s FROM Screen s ORDER BY s.name", Screen.class).getResultList();
    }

    public Screen create(Screen s) {
        em.persist(s);
        return s;
    }
}
