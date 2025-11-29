package com.example.booking.dao;

import com.example.booking.entities.Booking;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class BookingDao {
    @PersistenceContext(unitName = "ticketPU")
    private EntityManager em;

    public Booking find(Long id) { return em.find(Booking.class, id); }
    public List<Booking> findAll() { return em.createQuery("SELECT b FROM Booking b", Booking.class).getResultList(); }
    public Booking create(Booking b) { em.persist(b); return b; }
    public Booking update(Booking b) { return em.merge(b); }
}
