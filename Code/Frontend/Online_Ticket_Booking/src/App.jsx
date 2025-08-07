import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom"
import { AuthProvider } from "./contexts/AuthContext"
import { BookingProvider } from "./contexts/BookingContext"
import { ThemeProvider } from "./contexts/ThemeContext"
import Navbar from "./components/layout/Navbar"
import Footer from "./components/layout/Footer"
import HomePage from "./pages/HomePage"
import MovieDetailsPage from "./pages/MovieDetailsPage"
import ShowSelectionPage from "./pages/ShowSelectionPage"
import SeatSelectionPage from "./pages/SeatSelectionPage"
import CheckoutPage from "./pages/CheckoutPage"
import UserProfilePage from "./pages/UserProfilePage"
import AdminDashboard from "./pages/AdminDashboard"
import TheaterOwnerDashboard from "./pages/TheaterOwnerDashboard"
import LoginModal from "./components/auth/LoginModal"
import TicketPage from "./pages/TicketPage";



function App() {
  return (
    <ThemeProvider>
      <AuthProvider>
        <BookingProvider>
          <Router>
            <div className="app">
              <Navbar />
              <main className="main-content">
                <Routes>
                  <Route path="/" element={<HomePage />} />
                  <Route path="/admin" element={<AdminHomepage />} />
                  <Route path="/movie/:id" element={<MovieDetailsPage />} />
                  <Route path="/movie/:id/shows" element={<ShowSelectionPage />} />
                  <Route path="/movie/:id/seats" element={<SeatSelectionPage />} />
                  <Route path="/checkout" element={<CheckoutPage />} />
                  <Route path="/profile" element={<UserProfilePage />} />
                  <Route path="/admin" element={<AdminDashboard />} />
                  <Route path="/users" element={<ManageUsers />} />
                  <Route path="/theater-owner" element={<TheaterOwnerDashboard />} />
                  <Route path="*" element={<Navigate to="/" replace />} />
                  <Route path="/ticket/:bookingId" element={<TicketPage />} />
                  

                </Routes>
              </main>
              <Footer />
              <LoginModal />
            </div>
          </Router>
        </BookingProvider>
      </AuthProvider>
    </ThemeProvider>
  )
}

export default App
