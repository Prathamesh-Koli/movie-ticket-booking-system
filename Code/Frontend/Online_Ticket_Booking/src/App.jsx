import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom"
import ShowSelectionPage from "./pages/ShowSelectionPage"
import { BookingProvider } from "./contexts/BookingContext"
import SeatSelectionPage from "./pages/SeatSelectionPage"
import Login from './pages/Login';
import Register from './pages/Register';
import UserProfilePage from "./pages/UserProfilePage";

function App() {
  return (
    <BookingProvider>
      <Router>
      <div className="app">
        <main className="main-content">
          <Routes>
            <Route path="/" element={<Navigate to="/movie/1/shows" />} />
            
            <Route path="/movie/:id/shows" element={<ShowSelectionPage />} />
            <Route path='signin' element={<Login/>}/>
            <Route path='profile' element={<UserProfilePage/>}/>
            <Route path="signup" element={<Register/>}/>
          </Routes>
        </main>
      </div>
    </Router>
    </BookingProvider>
  )
}

export default App
