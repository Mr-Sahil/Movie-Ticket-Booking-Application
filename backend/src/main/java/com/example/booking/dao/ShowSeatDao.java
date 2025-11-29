package com.example.booking.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import com.example.booking.entities.ShowSeat;
import java.util.Optional;

@Stateless
public class ShowSeatDao {
    @PersistenceContext(unitName = "ticketPU")
    private EntityManager em;

    public Optional<ShowSeat> findByShowAndSeat(Long showId, Long seatId) {
        try {
            ShowSeat ss = em.createQuery("SELECT s FROM ShowSeat s WHERE s.show.id=:showId AND s.seat.id=:seatId", ShowSeat.class)
                .setParameter("showId", showId)
                .setParameter("seatId", seatId)
                .getSingleResult();
            return Optional.of(ss);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public ShowSeat findWithPessimisticLock(Long id) {
        return em.find(ShowSeat.class, id, LockModeType.PESSIMISTIC_WRITE);
    }

    public ShowSeat update(ShowSeat s) { return em.merge(s); }
}
