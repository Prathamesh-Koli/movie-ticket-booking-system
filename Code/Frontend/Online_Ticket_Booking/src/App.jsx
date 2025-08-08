import { BrowserRouter as Router, Routes, Route } from "react-router-dom"
import { ToastContainer } from 'react-toastify';
import ShowSelectionPage from "./pages/ShowSelectionPage"
import { BookingProvider } from "./contexts/BookingContext"
import SeatSelectionPage from "./pages/SeatSelectionPage"
import Login from './pages/Login';
import Register from './pages/Register';
import UserProfilePage from "./pages/UserProfilePage";
import TheatreManageShowsPage from "./pages/TheatreManageShowPage";

function App() {
  return (
    <BookingProvider>
      <Router>
      <div className="app">
        {/* <Navbar /> */}
        <main className="main-content">
          <Routes>
            <Route path="/movie/:id/seats" element={<SeatSelectionPage />} />
            <Route path="/movie/:id/shows" element={<ShowSelectionPage />} />
            <Route path='signin' element={<Login/>}/>
            <Route path='profile' element={<UserProfilePage/>}/>
            <Route path="signup" element={<Register/>}/>
            <Route path="/dashboard/shows" element={<TheatreManageShowsPage/>}/>
          </Routes>
        </main>
        {/* <Footer /> */}
        <ToastContainer/>
      </div>
    </Router>
    </BookingProvider>
  )
}

export default App