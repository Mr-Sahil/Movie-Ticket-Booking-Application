import React, { useEffect, useState } from "react";
import API from "./api";
import SeatSelector from "./components/SeatSelector";

export default function App() {
  const [shows, setShows] = useState([]);
  const [selectedShow, setSelectedShow] = useState(null);

  useEffect(() => {
    async function loadShows() {
      const res = await API.get("/shows");
      setShows(res.data);
      if (res.data.length) setSelectedShow(res.data[0]);
    }
    loadShows();
  }, []);

  return (
    <div style={{ padding: 20 }}>
      <h1>Movie Ticket Booking</h1>
      <div style={{ marginBottom: 20 }}>
        <select
          value={selectedShow?.id || ""}
          onChange={(e) => setSelectedShow(shows.find((s) => String(s.id) === e.target.value)) }
        >
          {shows.map((show) => (
            <option key={show.id} value={show.id}>
              {show.movie.title} — {new Date(show.startTime).toLocaleString()}
            </option>
          ))}
        </select>
      </div>
      {selectedShow && (
        <SeatSelector showId={selectedShow.id} />
      )}
    </div>
  );
}
