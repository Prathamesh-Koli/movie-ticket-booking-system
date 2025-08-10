import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import ShowSelectionPage from "./pages/ShowSelectionPage";
import { BookingProvider } from "./contexts/BookingContext";
import SeatSelectionPage from "./pages/SeatSelectionPage";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Footer from "./components/layout/Footer";
import AddShowPage from "./pages/AddShowPage"
import UserProfilePage from "./pages/UserProfilePage";
import { AuthProvider } from "./contexts/AuthContext";
import Navbar from "./components/layout/Navbar";
import { ThemeProvider } from "./contexts/ThemeContext";
import HomePage from "./pages/HomePage";
import SearchPage from "./pages/SearchPage";
import MovieDetailsPage from "./pages/MovieDetailsPage";
import ContactUs from "./pages/ContactUs";
import TermsOfUse from "./pages/TermsOfUse";
import PrivacyPolicy from "./pages/PrivacyPolicy";
import LayoutPage from "./pages/LayoutPage"
import AboutUs from "./pages/AboutUs";
import ManageTheaters from "./pages/ManageTheaters"
import TheaterListPage from "./pages/TheaterListPage"
import AddTheaterPage from "./pages/AddTheaterPage";
import TheatreManageShowsPage from './pages/TheatreManageShowPage';
import TheatreOwnerDashboard from "./pages/TheatreOwnerDashboard";

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
                  <Route path="/movie/:id" element={<MovieDetailsPage />} />
                  <Route path="/movie/:id/seats" element={<SeatSelectionPage />} />
                  <Route path="/movie/:id/shows" element={<ShowSelectionPage />} />
                  <Route path="/owner/theaters/:theaterId/add-show" element={<AddShowPage />} />
                  <Route path="/signin" element={<Login />} />
                  <Route path="/signup" element={<Register />} />
                  <Route path="/profile" element={<UserProfilePage />} />
                  <Route path="/search" element={<SearchPage />} />
                  <Route path="/owner/add-theater" element={<AddTheaterPage />} />
                  <Route path="/owner/theaters" element={<TheaterListPage />} />
                  <Route path="/owner/:theaterId/layout" element={<LayoutPage />} />
                  <Route path="/dashboard/shows" element={<TheatreManageShowsPage />} />
                  <Route path="/dashboard" element={<TheatreOwnerDashboard />}></Route>
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
          {/* admin routes */}
          <Router>
            <Layout>
              <Routes>
                <Route path="/admin" element={<Homepage />} />
                <Route path="/admin/movies" element={<ManageMovies />} />
                <Route path="/admin/theaters" element={<ManageTheaters />} />
                <Route path="/admin/users" element={<ManageUsers />} />
                {/* <Route path="/reports" element={<GenerateReports />} /> */}
              </Routes>
            </Layout>
          </Router>
        </BookingProvider>
      </AuthProvider>
    </ThemeProvider>
  );
}

export default App;
