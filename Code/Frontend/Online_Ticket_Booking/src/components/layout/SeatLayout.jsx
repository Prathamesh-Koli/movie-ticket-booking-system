"use client"

import { useMemo } from "react"
import PropTypes from "prop-types"
import "../../styles/SeatLayout.css"

const SeatLayout = ({ seats, onSeatClick}) => {
  
  const layoutByType = useMemo(() => {
    const flat = seats.flat()
    const byType = {}

    flat.forEach((seat) => {
      const { type, row } = seat
      if (!byType[type]) byType[type] = {}
      if (!byType[type][row]) byType[type][row] = []
      byType[type][row].push(seat)
    })

    // Sort seats numerically in each row
    Object.values(byType).forEach((rows) => {
      Object.values(rows).forEach((rowSeats) => {
        rowSeats.sort((a, b) => a.number - b.number)
      })
    })

    return byType
  }, [seats])

  const renderSeatSection = (rowSeats) => {
    return (
      <div className="seat-row-sections">
        <div className="seat-section">
          <div className="seats-group">
            {rowSeats.map((seat) => {
              const { id, status, price, type, number } = seat
              return (
                <button
                  key={id}
                  className={`cinema-seat ${status}`} // supports reserved
                  onClick={() => onSeatClick(seat)}
                  disabled={
                    status === "booked" ||
                    status === "unavailable" ||
                    status === "reserved"
                  }
                  title={`${seat.row}${seat.number} - ₹${price} (${type})`}
                >
                  <span className="seat-number">{number}</span>
                </button>
              )
            })}
          </div>
        </div>
      </div>
    )
  }
  

  const renderTier = (tierName, rowsObj) => {
    const sampleRow = rowsObj[Object.keys(rowsObj)[0]] || []
    const price = sampleRow.length > 0 ? sampleRow[0].price : 0

    return (
      <div key={tierName} className="pricing-tier">
        <div className="tier-header">
          <h6 className="tier-name">
            {tierName.toUpperCase()} (₹{price}.00)
          </h6>
        </div>
        <div className="tier-rows">
          {Object.keys(rowsObj)
            .sort()
            .map((row) => (
              <div key={row} className="cinema-row">
                <div className="row-label left">{row}</div>
                {renderSeatSection(rowsObj[row])}
                <div className="row-label right">{row}</div>
              </div>
            ))}
        </div>
      </div>
    )
  }

  return (
    <div className="cinema-seat-layout">
      <div className="screen-indicator">
        <div className="screen-bar">
          <small className="screen-text">SCREEN</small>
        </div>
      </div>

      <div className="seating-area">
      {["EXECUTIVE", "PREMIUM", "VIP"].map((type) =>
  layoutByType[type] ? renderTier(type, layoutByType[type]) : null
)}

      </div>

      <div className="seat-legend">
        <div className="legend-item">
          <div className="cinema-seat available"></div>
          <small>Available</small>
        </div>
        <div className="legend-item">
          <div className="cinema-seat reserved"></div>
          <small>Reserved</small>
        </div>
        <div className="legend-item">
          <div className="cinema-seat selected"></div>
          <small>Selected</small>
        </div>
        <div className="legend-item">
          <div className="cinema-seat booked"></div>
          <small>Booked</small>
        </div>
        <div className="legend-item">
          <div className="cinema-seat unavailable"></div>
          <small>Unavailable</small>
        </div>
      </div>
    </div>
  )
}

SeatLayout.propTypes = {
  seats: PropTypes.array.isRequired,
  onSeatClick: PropTypes.func.isRequired,
}

export default SeatLayout
