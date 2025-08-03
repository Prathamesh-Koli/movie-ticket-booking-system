"use client"

import { createContext, useContext, useState } from "react"

const BookingContext = createContext()

export const useBooking = () => {
  const context = useContext(BookingContext)
  if (!context) {
    throw new Error("useBooking must be used within a BookingProvider")
  }
  return context
}


export const BookingProvider = ({ children }) => {
  const [selectedSeats, setSelectedSeats] = useState([])
 
  const addToCart = (seat) => {
    setSelectedSeats((prev) => [...prev, { ...seat, status: "selected" }])
  }

  const removeFromCart = (seatId) => {
    setSelectedSeats((prev) => prev.filter((seat) => seat.id !== seatId))
  }

  const clearCart = () => {
    setSelectedSeats([])
  }


  return (
    <BookingContext.Provider
      value={{
        selectedSeats,
        addToCart,
        removeFromCart,
        clearCart   
      }}
    >
      {children}
    </BookingContext.Provider>
  )
}
