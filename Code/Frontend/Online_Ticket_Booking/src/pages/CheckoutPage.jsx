import { useEffect, useState } from "react"
import { Container, Row, Col, Card, Button, Alert, Spinner } from "react-bootstrap"
import { useBooking } from "../contexts/BookingContext"
import { useAuth } from "../contexts/AuthContext"
import { useNavigate } from "react-router-dom"
import axios from "axios"

const CheckoutPage = () => {
  const { selectedSeats, createBooking, clearCart, movieId, showId } = useBooking()
  const { user, setShowLoginModal } = useAuth()
  const navigate = useNavigate()

  //const [reservationId, setReservationId] = useState(null)
  const reservationId=3
  const [isProcessing, setIsProcessing] = useState(false)
  const [error, setError] = useState("")
  const [bookingComplete, setBookingComplete] = useState(null)

  useEffect(() => {
    const createReservation = async () => {
      if (!user || selectedSeats.length === 0 || !showId) return

      try {
        const response = await axios.post("http://localhost:8080/api/reservations", {
          userId: user.id,
          showId,
          showSeatIds: selectedSeats.map(seat => seat.showSeatId)
        })
        setReservationId(response.data.reservationId)
      } catch (err) {
        console.error("Reservation creation failed:", err)
        setError("Reservation failed. Please try again.")
      }
    }

    createReservation()
  }, [user, selectedSeats, showId])

  const subtotal = selectedSeats.reduce((sum, seat) => sum + seat.price, 0)
  const convenienceFee = Math.round(subtotal * 0.1)
  const totalAmount = subtotal + convenienceFee

  const handlePayment = async (e) => {
    e.preventDefault()
    setIsProcessing(true)
    setError("")

    if (!reservationId) {
      setError("Reservation not found. Cannot proceed with payment.")
      setIsProcessing(false)
      return
    }

    try {
      const receiptId = `rcpt_${Date.now()}`
      const response = await axios.post("http://localhost:8080/api/payment/create-order", {
        amount: totalAmount,
        currency: "INR",
        receipt: receiptId
      })

      const { orderId, amount, currency, key } = response.data

      const options = {
        key,
        amount,
        currency,
        name: "BookAR",
        description: "Movie Ticket Booking",
        order_id: orderId,
        handler: async function (razorResponse) {
          try {
            const { razorpay_order_id, razorpay_payment_id, razorpay_signature } = razorResponse

            const verifyResponse = await axios.post("http://localhost:8080/api/payment/verify", {
              razorpayOrderId: razorpay_order_id,
              razorpayPaymentId: razorpay_payment_id,
              razorpaySignature: razorpay_signature,
              reservationId,
              userId: user.id
            })

            const { bookingId } = verifyResponse.data
            const ticketDetails = {
              id: bookingId,
              movieId,
              showId,
              seats: selectedSeats,
              totalAmount,
              bookingDate: new Date().toISOString(),
              status: "confirmed"
            }

            createBooking(ticketDetails)
            clearCart()
            setBookingComplete(ticketDetails)
          } catch (verifyError) {
            console.error("Payment verification failed:", verifyError)
            setError("Payment verification failed. Your seats have been released.")
          }
        },
        prefill: {
          name: user.name || "",
          email: user.email || ""
        },
        theme: { color: "#3f51b5" }
      }

      if (window.Razorpay) {
        const rzp = new window.Razorpay(options)
        rzp.open()
      } else {
        alert("Razorpay SDK not loaded. Please try again.")
      }
    } catch (createError) {
      console.error("Order creation failed:", createError)
      setError("Unable to initiate payment. Please try again.")
    } finally {
      setIsProcessing(false)
    }
  }

  if (!user) {
    return (
      <Container className="py-5 text-center">
        <Alert variant="warning">
          <h4>Please login to continue</h4>
          <Button variant="primary" onClick={() => setShowLoginModal(true)}>Login Now</Button>
        </Alert>
      </Container>
    )
  }

  if (selectedSeats.length === 0) {
    return (
      <Container className="py-5 text-center">
        <Alert variant="info">
          <h4>No seats selected</h4>
          <Button variant="primary" onClick={() => navigate("/")}>Browse Movies</Button>
        </Alert>
      </Container>
    )
  }

  return (
    <Container className="py-4">
      {error && <Alert variant="danger">{error}</Alert>}
      <Row>
        <Col lg={8}>
          <Card className="mb-4">
            <Card.Header><h5>Payment</h5></Card.Header>
            <Card.Body>
              <p>Total to Pay: <strong>₹{totalAmount}</strong></p>
              <Button
                type="button"
                variant="primary"
                onClick={handlePayment}
                disabled={isProcessing || !reservationId}
              >
                {isProcessing ? <Spinner animation="border" size="sm" /> : `Pay ₹${totalAmount}`}
              </Button>

              {bookingComplete && (
                <Card className="mt-4">
                  <Card.Header><h5>Your Ticket</h5></Card.Header>
                  <Card.Body>
                    <p><strong>Booking ID:</strong> {bookingComplete.id}</p>
                    <p><strong>Status:</strong> {bookingComplete.status}</p>
                    <p><strong>Seats:</strong> {bookingComplete.seats.map(s => `${s.row}${s.number}`).join(", ")}</p>
                    <p><strong>Total Paid:</strong> ₹{bookingComplete.totalAmount}</p>
                    <p><strong>Booking Time:</strong> {new Date(bookingComplete.bookingDate).toLocaleString()}</p>
                    <Button variant="success" onClick={() => navigate(`/ticket/${bookingComplete.id}`)}>
                      View Full Ticket Page
                    </Button>
                  </Card.Body>
                </Card>
              )}
            </Card.Body>
          </Card>
        </Col>

        <Col lg={4}>
          <Card>
            <Card.Header><h5>Summary</h5></Card.Header>
            <Card.Body>
              <p>Seats Selected: {selectedSeats.length}</p>
              <p>Subtotal: ₹{subtotal}</p>
              <p>Convenience Fee: ₹{convenienceFee}</p>
              <hr />
              <p><strong>Total: ₹{totalAmount}</strong></p>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  )
}

export default CheckoutPage
