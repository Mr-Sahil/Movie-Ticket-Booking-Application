import axios from 'axios';

const API = axios.create({
  baseURL: process.env.REACT_APP_API_URL || 'http://localhost:8080/api'
});

export default API;

export async function fetchSeats(showId) {
  const res = await API.get(`/shows/${showId}/seats`)
  if (res.status !== 200) throw new Error('Failed to load seats')
  return res.data
}

export async function bookSeats(body) {
  const res = await API.post(`/bookings`, body)
  if (res.status !== 200) {
    const err = res.data
    throw new Error(err.error || 'Booking failed')
  }
  return res.data
}
