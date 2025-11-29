package com.example.booking.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import com.example.booking.entities.ShowSeat;
import java.util.List;

@Stateless
public class SeatDao {
    @PersistenceContext(unitName = "ticketPU")
    private EntityManager em;

    public List<ShowSeat> findByShowId(Long showId) {
        TypedQuery<ShowSeat> q = em.createQuery(
            "SELECT ss FROM ShowSeat ss LEFT JOIN FETCH ss.seat WHERE ss.show.id = :showId ORDER BY ss.seat.seatNumber",
            ShowSeat.class);
        q.setParameter("showId", showId);
        return q.getResultList();
    }
}
