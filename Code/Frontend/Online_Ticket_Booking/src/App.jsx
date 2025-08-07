import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { ToastContainer } from "react-toastify";

import { AuthProvider } from "./contexts/AuthContext";
import { BookingProvider } from "./contexts/BookingContext";
import { ThemeProvider } from "./contexts/ThemeContext";

import Navbar from "./components/layout/Navbar";
import Footer from "./components/layout/Footer";
import LoginModal from "./components/auth/LoginModal";

import HomePage from "./pages/HomePage";
import MovieDetailsPage from "./pages/MovieDetailsPage";
import ShowSelectionPage from "./pages/ShowSelectionPage";
import SeatSelectionPage from "./pages/SeatSelectionPage";
import CheckoutPage from "./pages/CheckoutPage";
import UserProfilePage from "./pages/UserProfilePage";
import AdminDashboard from "./pages/AdminDashboard";
import TheaterOwnerDashboard from "./pages/TheaterOwnerDashboard";
import TicketPage from "./pages/TicketPage";

import Login from "./pages/Login";
import Register from "./pages/Register";
import SearchPage from "./pages/SearchPage";
import ContactUs from "./pages/ContactUs";
import TermsOfUse from "./pages/TermsOfUse";
import PrivacyPolicy from "./pages/PrivacyPolicy";
import AboutUs from "./pages/AboutUs";


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
              <Route path="/admin" element={<AdminDashboard />} />
  <Route path="/users" element={<ManageUsers />} />
  <Route path="/theater-owner" element={<TheaterOwnerDashboard />} />

  {/* Movie-related Routes */}
  <Route path="/movie/:id" element={<MovieDetailsPage />} />
  <Route path="/movie/:id/shows" element={<ShowSelectionPage />} />
  <Route path="/movie/:id/seats" element={<SeatSelectionPage />} />

  {/* Auth Routes */}
  <Route path="/signin" element={<Login />} />
  <Route path="/signup" element={<Register />} />

  {/* User Routes */}
  <Route path="/profile" element={<UserProfilePage />} />
  <Route path="/checkout" element={<CheckoutPage />} />
  <Route path="/ticket/:bookingId" element={<TicketPage />} />

  {/* General Routes */}
  <Route path="/search" element={<SearchPage />} />
  <Route path="/contact-us" element={<ContactUs />} />
  <Route path="/terms-of-use" element={<TermsOfUse />} />
  <Route path="/privacy-policy" element={<PrivacyPolicy />} />
  <Route path="/about-us" element={<AboutUs />} />

  {/* Fallback Route */}
  <Route path="*" element={<Navigate to="/" replace />} />
</Routes>
</main>
<Footer />
<LoginModal />
<ToastContainer />
            </div>
          </Router>
        </BookingProvider>
      </AuthProvider>
    </ThemeProvider>
  );
}

export default App;
