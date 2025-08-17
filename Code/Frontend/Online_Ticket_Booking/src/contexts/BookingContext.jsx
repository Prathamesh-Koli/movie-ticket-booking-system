"use client"

import { createContext, useContext, useState, useEffect } from "react"
import axios from "axios"

const BookingContext = createContext()

export const useBooking = () => {
  const context = useContext(BookingContext)
  if (!context) {
    throw new Error("useBooking must be used within a BookingProvider")
  }
  return context
}


export const BookingProvider = ({ children }) => {
  const [movies, setMovies] = useState([])
  const [loading, setLoading] = useState(true)

 
 
  useEffect(() => {
    axios.get("http://localhost:8080/user/movies")
      .then((res) => {
        setMovies(res.data)
      })
      .catch((err) => console.error("Error fetching movies", err))
      .finally(() => setLoading(false))
  }, [])
  
  
  return (
    <BookingContext.Provider
      value={{
        movies,
        theaters: [], 
        shows: [],
        loading   
      }}
    >
      {children}
    </BookingContext.Provider>
  )
}
