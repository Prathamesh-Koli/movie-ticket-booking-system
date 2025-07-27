import { BrowserRouter as Router, Routes, Route } from "react-router-dom"
import ShowSelectionPage from "./pages/ShowSelectionPage"

function App() {
  return (
    <Router>
      <div className="app">
        {/* <Navbar /> */}
        <main className="main-content">
          <Routes>
            <Route path="/movie/:id/shows" element={<ShowSelectionPage />} />
          </Routes>
        </main>
        {/* <Footer /> */}
      </div>
    </Router>
  )
}

export default App