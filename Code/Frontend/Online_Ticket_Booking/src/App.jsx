import { BrowserRouter as Router, Routes, Route } from "react-router-dom"
import ShowSelectionPage from "./pages/ShowSelectionPage"
import { BookingProvider } from "./contexts/BookingContext"
import SeatSelectionPage from "./pages/SeatSelectionPage"
import Login from './pages/Login';
import Register from './pages/Register';
import Footer from "./components/layout/Footer";
import UserProfilePage from "./pages/UserProfilePage";
import MovieDetailsPage from "./pages/MovieDetailsPage";
import ContactUs from "./pages/ContactUs";
import TermsOfUse from "./pages/TermsOfUse";
import PrivacyPolicy from "./pages/PrivacyPolicy";
import AboutUs from "./pages/AboutUs";

function App() {
  return (
    <Router>
      <div className="app">
        {/* <Navbar /> */}
        <main className="main-content">
          <Routes>
            <Route path="/movie/:id" element={<MovieDetailsPage />} />
            <Route path="/movie/:id/seats" element={<SeatSelectionPage />} />
            <Route path="/movie/:id/shows" element={<ShowSelectionPage />} />
            <Route path='signin' element={<Login/>}/>
            <Route path='profile' element={<UserProfilePage/>}/>
            <Route path="signup" element={<Register/>}/>
            {/* Footer pages */}
            <Route path="/contact-us" element={<ContactUs />} />
            <Route path="/terms-of-use" element={<TermsOfUse />} />
            <Route path="/privacy-policy" element={<PrivacyPolicy />} />    
            <Route path="/about-us" element={<AboutUs />} />
          </Routes>
        </main>
        <Footer />
        <ToastContainer />
      </div>
    </Router>
  )
}

export default App