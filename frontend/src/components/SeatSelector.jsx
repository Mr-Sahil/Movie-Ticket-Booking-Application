import React, { useEffect, useState } from "react";
import API from "../api";

export default function SeatSelector({ showId }) {
  const [seats, setSeats] = useState([]);
  const [selected, setSelected] = useState(new Set());

  useEffect(() => {
    loadSeats();
  }, [showId]);

  async function loadSeats() {
    const res = await API.get(`/shows/${showId}/seats`);
    setSeats(res.data);
    setSelected(new Set());
  }

  function toggleSeat(seat) {
    if (seat.booked) return;
    const copy = new Set(selected);
    if (copy.has(seat.id)) copy.delete(seat.id);
    else copy.add(seat.id);
    setSelected(copy);
  }

  async function bookSeats() {
    const body = { userId: 1, showId, seatIds: Array.from(selected) };
    try {
      await API.post(`/bookings`, body);
      alert("Booking successful!");
    } catch (err) {
      alert("Failed: " + (err.response?.data?.error || err.message));
    }
    loadSeats();
  }

  return (
    <div>
      <div style={{ display: "grid", gridTemplateColumns: "repeat(8, 40px)", gap: 10 }}>
        {seats.map((s) => (
          <button
            key={s.id}
            onClick={() => toggleSeat(s)}
            disabled={s.booked}
            style={{ padding: 10, background: s.booked ? "#aaa" : selected.has(s.id) ? "#4caf50" : "white" }}
          >
            {s.seatNumber}
          </button>
        ))}
      </div>
      <button onClick={bookSeats} disabled={selected.size === 0} style={{ marginTop: 20 }}>
        Book ({selected.size})
      </button>
    </div>
  );
}
