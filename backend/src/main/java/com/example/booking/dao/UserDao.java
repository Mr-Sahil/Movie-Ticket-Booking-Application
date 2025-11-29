package com.example.booking.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;
import com.example.booking.entities.User;
import java.util.Optional;

@Stateless
public class UserDao {
    @PersistenceContext(unitName = "ticketPU")
    private EntityManager em;

    public Optional<User> findById(Long id) {
        try {
            User u = em.find(User.class, id);
            return Optional.ofNullable(u);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public Optional<User> findByEmail(String email) {
        try {
            User u = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(u);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public User create(User u) {
        em.persist(u);
        return u;
    }
}
