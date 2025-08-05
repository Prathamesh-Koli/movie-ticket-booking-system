import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom"
import { ToastContainer } from 'react-toastify';
import ShowSelectionPage from "./pages/ShowSelectionPage"
import { BookingProvider } from "./contexts/BookingContext"
import SeatSelectionPage from "./pages/SeatSelectionPage"
import Login from './pages/Login';
import Register from './pages/Register';
import UserProfilePage from "./pages/UserProfilePage";
import { AuthProvider } from "./contexts/AuthContext"
import Navbar from "./components/layout/Navbar"
import { ThemeProvider } from "./contexts/ThemeContext"
import HomePage from "./pages/HomePage"
import SearchPage from "./pages/SearchPage"

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

                <Route path="/movie/:id/seats" element={<SeatSelectionPage />} />
                <Route path="/movie/:id/shows" element={<ShowSelectionPage />} />
                <Route path='signin' element={<Login />} />
                <Route path='profile' element={<UserProfilePage />} />
                <Route path="signup" element={<Register />} />
                <Route path="/search" element={<SearchPage />} />
                {/* <Route path="*" element={<Navigate to="/" replace />} /> */}
              </Routes>
            </main>
            {/* <Footer /> */}
            <ToastContainer />
          </div>
        </Router>
      </BookingProvider>
      </AuthProvider>
    </ThemeProvider>
  )
}

export default App