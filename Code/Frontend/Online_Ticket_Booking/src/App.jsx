<<<<<<< HEAD
import { useEffect } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { ToastContainer } from "react-toastify";

import { AuthProvider } from "./contexts/AuthContext";
import { BookingProvider } from "./contexts/BookingContext";
import { ThemeProvider, useTheme } from "./contexts/ThemeContext";



import Navbar from "./components/layout/Navbar";
import Footer from "./components/layout/Footer";
//import LoginModal from "./components/auth/LoginModal";

import HomePage from "./pages/HomePage";
import MovieDetailsPage from "./pages/MovieDetailsPage";
import ShowSelectionPage from "./pages/ShowSelectionPage";
import SeatSelectionPage from "./pages/SeatSelectionPage";
import CheckoutPage from "./pages/CheckoutPage";
import UserProfilePage from "./pages/UserProfilePage";
import AdminDashboard from "./pages/AdminDashboard";
//import TheaterOwnerDashboard from "./pages/TheaterOwnerDashboard";
import TicketPage from "./pages/TicketPage";

import Login from "./pages/Login";
import Register from "./pages/Register";
import SearchPage from "./pages/SearchPage";
import ContactUs from "./pages/ContactUs";
import TermsOfUse from "./pages/TermsOfUse";
import PrivacyPolicy from "./pages/PrivacyPolicy";
import AboutUs from "./pages/AboutUs";
import ManageUsers from "./pages/ManageUsers";
import TheatreManageShowsPage from './pages/TheatreManageShowPage';

function ThemeBodyClassSetter() {
  const { theme } = useTheme();

  useEffect(() => {
    if (theme === "dark") {
      document.body.classList.add("dark-theme");
    } else {
      document.body.classList.remove("dark-theme");
    }
  }, [theme]);

  return null; // No UI output
}
=======
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

>>>>>>> 7994ac8 (added admin dashboard, manage users page, modified dataservice)


function App() {
  return (
    <ThemeProvider>
<<<<<<< HEAD
      <ThemeBodyClassSetter />
=======
>>>>>>> 7994ac8 (added admin dashboard, manage users page, modified dataservice)
      <AuthProvider>
        <BookingProvider>
          <Router>
            <div className="app">
              <Navbar />
              <main className="main-content">
                <Routes>
<<<<<<< HEAD
                  {/* Admin */}
                  <Route path="/admin" element={<AdminDashboard />} />
                  <Route path="/users" element={<ManageUsers />} />
                  {/* <Route path="/theater-owner" element={<TheaterOwnerDashboard />} /> */}

                  {/* Movie */}
                  <Route path="/" element={<HomePage />} />
                  <Route path="/movie/:id" element={<MovieDetailsPage />} />
                  <Route path="/movie/:id/shows" element={<ShowSelectionPage />} />
                  <Route path="/movie/:id/seats" element={<SeatSelectionPage />} />

                  {/* Auth */}
                  <Route path="/login" element={<Login />} />
                  <Route path="/register" element={<Register />} />

                  {/* User */}
                  <Route path="/profile" element={<UserProfilePage />} />
                  <Route path="/checkout" element={<CheckoutPage />} />
                  <Route path="/ticket/:bookingId" element={<TicketPage />} />

                  {/* General */}
                  <Route path="/search" element={<SearchPage />} />
                  <Route path="/dashboard/shows" element={<TheatreManageShowsPage />} />
                  <Route path="/contact-us" element={<ContactUs />} />
                  <Route path="/terms-of-use" element={<TermsOfUse />} />
                  <Route path="/privacy-policy" element={<PrivacyPolicy />} />
                  <Route path="/about-us" element={<AboutUs />} />

                  {/* Fallback */}
                  <Route path="*" element={<Navigate to="/" replace />} />
                </Routes>
              </main>

              <Footer />
              <ToastContainer />
=======
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
>>>>>>> 7994ac8 (added admin dashboard, manage users page, modified dataservice)
            </div>
          </Router>
        </BookingProvider>
      </AuthProvider>
    </ThemeProvider>
<<<<<<< HEAD
  );
=======
  )
>>>>>>> 7994ac8 (added admin dashboard, manage users page, modified dataservice)
}

export default App;
