import axios from "axios";

const BASE_URL = "http://localhost:8080/api/admin"; 

class DataService {
  // Dashboard
  getDashboardStats() {
    return axios.get(`${BASE_URL}/dashboard-stats`);
  }

  // Movies
  getMovies() {
    return axios.get(`${BASE_URL}/movies`);
  }

  addMovie(movie) {
    return axios.post(`${BASE_URL}/movies`, movie);
  }

  updateMovie(id, updatedMovie) {
    return axios.put(`${BASE_URL}/movies/${id}`, updatedMovie);
  }

  deleteMovie(id) {
    return axios.delete(`${BASE_URL}/movies/${id}`);
  }

  // Theaters
  getTheaters() {
    return axios.get(`${BASE_URL}/theaters`);
  }

  updateTheaterStatus(id, status) {
    return axios.post(`${BASE_URL}/theaters/${id}/status`, { status });
  }

  deleteTheater(id) {
    return axios.delete(`${BASE_URL}/theaters/${id}`);
  }

  // Users
  getUsers() {
    return axios.get(`${BASE_URL}/users`);
  }

  toggleUserStatus(id) {
    return axios.post(`${BASE_URL}/users/${id}/toggle-status`);
  }

  // Reports
  getReportData(startDate, endDate) {
    return axios.get(`${BASE_URL}/reports`, {
      params: { startDate, endDate },
    });
  }
}

export default new DataService();
