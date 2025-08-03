import { BrowserRouter as Router, Routes, Route } from "react-router-dom"
import ShowSelectionPage from "./pages/ShowSelectionPage"
import { BookingProvider } from "./contexts/BookingContext"
import SeatSelectionPage from "./pages/SeatSelectionPage"

function App() {
  return (
    <BookingProvider>
      <Router>
      <div className="app">
        {/* <Navbar /> */}
        <main className="main-content">
          <Routes>
            <Route path="/movie/:id/shows" element={<ShowSelectionPage />}
             />
            <Route path="/movie/:id/seats" element={<SeatSelectionPage />} />
          </Routes>
        </main>
        {/* <Footer /> */}
      </div>
    </Router>
    </BookingProvider>
    
  )
}

export default App